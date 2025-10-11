package com.commerce.core.product.service;

import com.commerce.core.common.exception.CommerceException;
import com.commerce.core.common.exception.ExceptionStatus;
import com.commerce.core.event.EventTopic;
import com.commerce.core.event.producer.EventSender;
import com.commerce.core.product.domain.ProductStockDao;
import com.commerce.core.product.domain.entity.Product;
import com.commerce.core.product.domain.entity.ProductStock;
import com.commerce.core.product.domain.entity.ProductStockHistory;
import com.commerce.core.product.service.request.ProductStockServiceRequest;
import com.commerce.core.product.type.ProductStockProcessStatus;
import com.commerce.core.product.service.request.ProductViewServiceRequest;
import com.commerce.core.product.type.ProductStockSummary;
import com.commerce.core.product.type.ProductViewStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductStockService {

    private final Long STOCK_SOLD_OUT_COUNT = 0L;
    private final ProductStockDao productStockDao;
    private final ProductService productService;
    private final EventSender eventSender;

    @Transactional
    public ProductStock productStockAdjustment(ProductStockServiceRequest request) {
        // 1. Product Exists Check
        Product product = productService.selectProduct(request.productSeq())
                .orElseThrow(() -> new CommerceException(ExceptionStatus.ENTITY_IS_EMPTY));

        // 2. Product Stock Adjustment
        final boolean isConsume = request.productStockProcessStatus() == ProductStockProcessStatus.CONSUME;
        final Long stock = Math.abs(request.stock()) * (isConsume ? -1 : 1);

        ProductStock productStock = this.stockAdjustmentProcess(product, isConsume, stock);

        productStockDao.save(productStock);
        ProductStockHistory productStockHistory = productStock.generateHistoryEntity(stock, request.productStockProcessStatus());
        productStockDao.productStockHistorySave(productStockHistory);

        // 3. Event Send(Product View Mongo DB)
        this.productStockEventSend(product.getProductInfo().getProductInfoSeq(), productStock.getStock(), isConsume);

        return productStock;
    }

    private ProductStock stockAdjustmentProcess(Product product, boolean isConsume, Long stock) {
        Optional<ProductStock> productStockOptional = productStockDao.lockFindById(product.getProductSeq());

        if(productStockOptional.isPresent()) {
            ProductStock productStock = productStockOptional.get();
            productStock.inventoryAdjustment(stock);

            if(STOCK_SOLD_OUT_COUNT > productStock.getStock())
                throw new CommerceException(ExceptionStatus.SOLD_OUT);

            return productStock;
        }

        if(isConsume) throw new CommerceException(ExceptionStatus.SOLD_OUT);

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

        ProductViewServiceRequest productViewDto = ProductViewServiceRequest.builder()
                .productInfoSeq(productInfoSeq)
                .productViewStatus(ProductViewStatus.STOCK_ADJUSTMENT)
                .build();
        eventSender.send(EventTopic.SYNC_PRODUCT, productViewDto);
    }

    private boolean isEventSendTarget(Long stock) {
        if (stock.compareTo(5L) > 0) return false;
        return true;
    }

    @Transactional(readOnly = true)
    public Optional<ProductStock> selectProductStock(Long productSeq) {
        return productStockDao.productStockFindById(productSeq);
    }

    public ProductStockSummary productStockSummary(Long stock) {
        if(stock.equals(0L))
            return ProductStockSummary.NOT_IN_STOCK;
        if(stock.compareTo(0L) > 0 && stock.compareTo(5L) < 0)
            return ProductStockSummary.SMALL_STOCK;

        return ProductStockSummary.MANY_STOCK;
    }

}
