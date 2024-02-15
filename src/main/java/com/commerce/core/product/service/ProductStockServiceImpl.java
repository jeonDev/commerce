package com.commerce.core.product.service;

import com.commerce.core.common.exception.CommerceException;
import com.commerce.core.common.exception.ExceptionStatus;
import com.commerce.core.event.producer.EventSender;
import com.commerce.core.product.entity.Product;
import com.commerce.core.product.entity.ProductStock;
import com.commerce.core.product.repository.ProductStockHistoryRepository;
import com.commerce.core.product.repository.ProductStockRepository;
import com.commerce.core.product.vo.ProductStockDto;
import com.commerce.core.product.vo.ProductStockProcessStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductStockServiceImpl implements ProductStockService {

    private final ProductStockRepository productStockRepository;
    private final ProductStockHistoryRepository productStockHistoryRepository;
    private final ProductService productService;
    private final EventSender eventSender;

    @Override
    public ProductStock productStockAdjustment(ProductStockDto dto) {
        // 1. Product Exists Check
        Product product = productService.selectProduct(dto.getProductSeq())
                .orElseThrow(() -> new CommerceException(ExceptionStatus.ENTITY_IS_EMPTY));

        // 2. Product Stock Adjustment
        final boolean isConsume = dto.getProductStockProcessStatus() == ProductStockProcessStatus.CONSUME;
        final Long stock = Math.abs(dto.getStock()) * (isConsume ? -1 : 1);

        ProductStock productStock = this.stockAdjustmentProcess(product, isConsume, stock);

        if(STOCK_SOLD_OUT_COUNT >= productStock.getStock())
            throw new CommerceException(ExceptionStatus.SOLD_OUT);

        productStock = productStockRepository.save(productStock);

        // 3. History Save
        this.saveHistoryEntity(productStock, stock, dto.getProductStockProcessStatus());

        // 4. Event Send(Product View Mongo DB)
        this.productStockEventSend();

        return productStock;
    }

    private ProductStock stockAdjustmentProcess(Product product, boolean isConsume, Long stock) {
        Optional<ProductStock> productStockOptional = productStockRepository.findById(product.getProductSeq());

        if(productStockOptional.isPresent()) {
            ProductStock productStock = productStockOptional.get();
            productStock.inventoryAdjustment(stock);
            return productStock;
        }

        if(isConsume) throw new CommerceException(ExceptionStatus.SOLD_OUT);

        return ProductStock.builder()
                .product(product)
                .stock(stock)
                .build();
    }

    private void saveHistoryEntity(ProductStock entity, Long stock, ProductStockProcessStatus productStockProcessStatus) {
        productStockHistoryRepository.save(entity.generateHistoryEntity(stock, productStockProcessStatus));
    }

    /**
     * Product View Event Send
     */
    private void productStockEventSend() {
        // TODO: Event Send 기준
        // stock 0~5 : 5개미만
        // 기존 재고 5 이하 & 변경 재고 5 이상 : 재고있음
        // └ 기존 데이터 없을 경우도 포함.
        // 이벤트를 stock 포함해서 보낼지, 이벤트를 보내고 event에서 처리할 지 결정 필요.

//        ProductViewDto productViewDto = ProductViewDto.builder()
//                .build();
//        eventSender.send(EventTopic.SYNC_PRODUCT.getTopic(), productViewDto);
    }

    @Override
    public Optional<ProductStock> selectProductStock(ProductStockDto dto) {
        return productStockRepository.findById(dto.getProductSeq());
    }

}
