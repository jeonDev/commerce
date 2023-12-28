package com.commerce.core.service.product;

import com.commerce.core.entity.ProductStock;
import com.commerce.core.vo.product.ProductStockDto;

/**
 * 상품 재고 Service
 */
public interface ProductStockService {
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
