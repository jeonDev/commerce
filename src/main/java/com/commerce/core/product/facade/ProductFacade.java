package com.commerce.core.product.facade;

import com.commerce.core.product.domain.entity.ProductInfo;
import com.commerce.core.product.service.ProductService;
import com.commerce.core.product.service.request.ProductServiceRequest;
import com.commerce.core.product.service.response.ProductServiceResponse;
import org.springframework.stereotype.Component;

@Component
public class ProductFacade {

    private final ProductService productService;

    public ProductFacade(ProductService productService) {
        this.productService = productService;
    }

    public ProductServiceResponse add(ProductServiceRequest request) {
        ProductInfo productInfo = productService.add(request);

        return ProductServiceResponse.builder()
                .productInfoSeq(productInfo.getProductInfoSeq())
                .productName(productInfo.getProductName())
                .productDetail(productInfo.getProductDetail())
                .price(productInfo.getPrice())
                .build();
    }
}
