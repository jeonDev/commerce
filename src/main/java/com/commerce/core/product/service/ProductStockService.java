package com.commerce.core.product.service;

import com.commerce.core.product.entity.ProductStock;
import com.commerce.core.product.vo.ProductStockDto;

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
     * @param dto
     * @return
     */
    ProductStock add(ProductStockDto dto);

    /**
     * Product Stock Consume
     * @param dto
     * @return
     */
    ProductStock consume(ProductStockDto dto);

    /**
     * Select Product Stock
     * @param dto
     * @return
     */
    ProductStock selectProductStock(ProductStockDto dto);
}
