package com.commerce.core.product.service;

import com.commerce.core.product.entity.ProductStock;
import com.commerce.core.product.vo.ProductStockDto;

import java.util.Optional;

public interface ProductStockService {

    Long STOCK_SOLD_OUT_COUNT = 0L;

    ProductStock productStockAdjustment(ProductStockDto dto);

    Optional<ProductStock> selectProductStock(ProductStockDto dto);
}
