package com.commerce.core.product.facade;

import com.commerce.core.product.service.ProductStockService;
import com.commerce.core.product.domain.type.ProductStockProcessStatus;
import org.springframework.stereotype.Component;

@Component
public class ProductStockFacade {

    private final ProductStockService productStockService;

    public ProductStockFacade(ProductStockService productStockService) {
        this.productStockService = productStockService;
    }

    public void adjustment(Long productOptionSeq,
                           ProductStockProcessStatus productStockProcessStatus,
                           Long stock
    ) {
        productStockService.productStockAdjustment(productOptionSeq, productStockProcessStatus, stock);
    }
}
