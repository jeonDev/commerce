package com.commerce.core.product.service;

import com.commerce.core.product.entity.ProductStock;
import com.commerce.core.product.vo.ProductStockDto;

import java.util.Optional;

/**
 * 상품 재고 Service
 */
public interface ProductStockService {

    /**
     * 품절 재고 수량
     */
    Long STOCK_SOLD_OUT_COUNT = 0L;
    
    /**
     * Product Stock Add
     */
    ProductStock add(ProductStockDto dto);

    /**
     * Product Stock Consume
     */
    ProductStock consume(ProductStockDto dto);

    /**
     * Select Product Stock
     */
    Optional<ProductStock> selectProductStock(ProductStockDto dto);
}
