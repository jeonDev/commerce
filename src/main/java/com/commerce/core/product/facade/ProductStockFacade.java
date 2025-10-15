package com.commerce.core.product.facade;

import com.commerce.core.product.service.ProductStockService;
import com.commerce.core.product.service.request.ProductStockServiceRequest;
import org.springframework.stereotype.Component;

@Component
public class ProductStockFacade {

    private final ProductStockService productStockService;

    public ProductStockFacade(ProductStockService productStockService) {
        this.productStockService = productStockService;
    }

    public void adjustment(ProductStockServiceRequest request) {
        productStockService.productStockAdjustment(request);
    }
}
