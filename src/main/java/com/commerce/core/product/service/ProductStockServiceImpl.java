package com.commerce.core.product.service;

import com.commerce.core.common.config.redis.RedisKeyType;
import com.commerce.core.common.config.redis.RedissonLockTarget;
import com.commerce.core.common.exception.CommerceException;
import com.commerce.core.common.exception.ExceptionStatus;
import com.commerce.core.event.EventTopic;
import com.commerce.core.event.producer.EventSender;
import com.commerce.core.product.entity.Product;
import com.commerce.core.product.entity.ProductStock;
import com.commerce.core.product.entity.ProductStockHistory;
import com.commerce.core.product.repository.ProductStockHistoryRepository;
import com.commerce.core.product.repository.ProductStockRepository;
import com.commerce.core.product.vo.ProductStockDto;
import com.commerce.core.product.vo.ProductStockProcessStatus;
import com.commerce.core.product.vo.ProductViewDto;
import com.commerce.core.product.vo.ProductViewStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductStockServiceImpl implements ProductStockService {

    private final ProductStockRepository productStockRepository;
    private final ProductStockHistoryRepository productStockHistoryRepository;
    private final ProductService productService;
    private final EventSender eventSender;

    @RedissonLockTarget(RedisKeyType.PRODUCT_STOCK)
    @Transactional
    @Override
    public ProductStock productStockAdjustment(ProductStockDto dto) {
        // 1. Product Exists Check
        Product product = productService.selectProduct(dto.getProductSeq())
                .orElseThrow(() -> new CommerceException(ExceptionStatus.ENTITY_IS_EMPTY));

        // 2. Product Stock Adjustment
        final boolean isConsume = dto.getProductStockProcessStatus() == ProductStockProcessStatus.CONSUME;
        final Long stock = Math.abs(dto.getStock()) * (isConsume ? -1 : 1);

        ProductStock productStock = this.stockAdjustmentProcess(product, isConsume, stock);

        productStock = productStockRepository.save(productStock);
        ProductStockHistory productStockHistory = productStock.generateHistoryEntity(stock, dto.getProductStockProcessStatus());
        productStockHistoryRepository.save(productStockHistory);

        // 3. Event Send(Product View Mongo DB)
        this.productStockEventSend(product.getProductInfo().getProductInfoSeq(), productStock.getStock(), isConsume);

        return productStock;
    }

    private ProductStock stockAdjustmentProcess(Product product, boolean isConsume, Long stock) {
        Optional<ProductStock> productStockOptional = productStockRepository.findById(product.getProductSeq());

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

        ProductViewDto productViewDto = ProductViewDto.builder()
                .productInfoSeq(productInfoSeq)
                .productViewStatus(ProductViewStatus.STOCK_ADJUSTMENT)
                .build();
        eventSender.send(EventTopic.SYNC_PRODUCT.getTopic(), productViewDto);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<ProductStock> selectProductStock(Long productSeq) {
        return productStockRepository.findById(productSeq);
    }

}
