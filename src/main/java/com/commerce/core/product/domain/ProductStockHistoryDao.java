package com.commerce.core.product.domain;

import com.commerce.core.product.domain.entity.ProductStockHistory;

public interface ProductStockHistoryDao {
    ProductStockHistory save(ProductStockHistory productStockHistory);
}
