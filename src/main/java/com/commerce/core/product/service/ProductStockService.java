package com.commerce.core.product.service;

import com.commerce.core.product.domain.entity.ProductStock;
import com.commerce.core.product.service.request.ProductStockServiceRequest;
import com.commerce.core.product.type.ProductStockSummary;

import java.util.Optional;

public interface ProductStockService {

    Long STOCK_SOLD_OUT_COUNT = 0L;

    ProductStock productStockAdjustment(ProductStockServiceRequest request);

    Optional<ProductStock> selectProductStock(Long productSeq);

    default boolean isEventSendTarget(Long stock) {
        if (stock.compareTo(5L) > 0) return false;
        return true;
    }

    default ProductStockSummary productStockSummary(Long stock) {
        if(stock.equals(0L)) 
            return ProductStockSummary.NOT_IN_STOCK;
        if(stock.compareTo(0L) > 0 && stock.compareTo(5L) < 0)
            return ProductStockSummary.SMALL_STOCK;

        return ProductStockSummary.MANY_STOCK;
    }
}
