package com.commerce.core.product.domain;

import com.commerce.core.product.domain.entity.ProductStock;
import com.commerce.core.product.domain.entity.ProductStockHistory;

import java.util.Optional;

public interface ProductStockDao {
    void save(ProductStock productStock);

    void productStockHistorySave(ProductStockHistory productStockHistory);

    Optional<ProductStock> lockFindById(Long productSeq);

    Optional<ProductStock> productStockFindById(Long productSeq);
}
