package com.commerce.core.product.service;

import com.commerce.core.common.exception.CommerceException;
import com.commerce.core.common.exception.ExceptionStatus;
import com.commerce.core.product.domain.ProductDao;
import com.commerce.core.product.domain.ProductStockDao;
import com.commerce.core.product.domain.entity.Product;
import com.commerce.core.product.domain.entity.ProductStock;
import com.commerce.core.product.domain.entity.ProductStockHistory;
import com.commerce.core.product.service.request.ProductStockServiceRequest;
import com.commerce.core.product.type.ProductStockProcessStatus;
import com.commerce.core.event.request.ProductViewEventRequest;
import com.commerce.core.product.type.ProductViewStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class ProductStockService {

    private final Long STOCK_SOLD_OUT_COUNT = 0L;
    private final ProductStockDao productStockDao;
    private final ProductDao productDao;
    private final ApplicationEventPublisher publisher;

    public ProductStockService(ProductStockDao productStockDao,
                               ProductDao productDao,
                               ApplicationEventPublisher publisher) {
        this.productStockDao = productStockDao;
        this.productDao = productDao;
        this.publisher = publisher;
    }

    @Transactional
    public ProductStockHistory productStockAdjustment(ProductStockServiceRequest request) {
        // 1. Product Exists Check
        var product = productDao.findById(request.productSeq())
                .orElseThrow(() -> new CommerceException(ExceptionStatus.ENTITY_IS_EMPTY));

        // 2. Product Stock Adjustment
        final boolean isConsume = request.productStockProcessStatus() == ProductStockProcessStatus.CONSUME;
        final Long stock = Math.abs(request.stock()) * (isConsume ? -1 : 1);

        var productStock = this.stockAdjustmentProcess(product, isConsume, stock);

        productStockDao.save(productStock);
        var productStockHistory = productStock.generateHistoryEntity(stock, request.productStockProcessStatus());
        productStockDao.productStockHistorySave(productStockHistory);

        // 3. Event Send(Product View Mongo DB)
        this.productStockEventSend(product.getProductInfo().getProductInfoSeq(), productStock.getStock(), isConsume);

        return productStockHistory;
    }

    private ProductStock stockAdjustmentProcess(Product product, boolean isConsume, Long stock) {
        var optionalProductStock = productStockDao.lockFindById(product.getProductSeq());

        if (optionalProductStock.isPresent()) {
            ProductStock productStock = optionalProductStock.get();
            productStock.inventoryAdjustment(stock);

            if (STOCK_SOLD_OUT_COUNT > productStock.getStock())
                throw new CommerceException(ExceptionStatus.SOLD_OUT);

            return productStock;
        }

        if (isConsume) throw new CommerceException(ExceptionStatus.SOLD_OUT);

        return ProductStock.builder()
                .product(product)
                .stock(stock)
                .build();
    }

    /**
     * Product View Event Send
     *  add -> event Send
     *  consume -> 5 under Event Sends
     */
    private void productStockEventSend(Long productInfoSeq, Long stock, boolean isConsume) {
        boolean isEventSend = true;

        if (isConsume) {
            isEventSend = isEventSendTarget(stock);
        }

        if (!isEventSend) return;

        var productViewDto = ProductViewEventRequest.builder()
                .productInfoSeq(productInfoSeq)
                .productViewStatus(ProductViewStatus.STOCK_ADJUSTMENT)
                .build();
        publisher.publishEvent(productViewDto);
    }

    private boolean isEventSendTarget(Long stock) {
        return stock.compareTo(5L) <= 0;
    }

}
