package com.commerce.core.service.product;

import com.commerce.core.entity.ProductStock;
import com.commerce.core.vo.product.ProductStockDto;

/**
 * 상품 재고 Service
 */
public interface ProductStockService {

    /**
     * 품절 재고 수량
     */
    Long STOCK_SOLD_OUT_COUNT = 0L;
    
    /**
     * Product Stock Adjustment
     * @param dto
     * @return
     */
    ProductStock adjustment(ProductStockDto dto);

    /**
     * Select Product Stock
     * @param dto
     * @return
     */
    ProductStock selectProductStock(ProductStockDto dto);
}
