package com.commerce.core.product.service.response;

import com.commerce.core.api.response.ProductInfoResponse;
import com.commerce.core.product.vo.ProductOptions;
import lombok.Builder;

import java.util.List;

@Builder
public record ProductDetailServiceResponse(
        Long productInfoSeq,
        String productName,
        String productDetail,
        Long price,
        List<ProductOptions> productOptions
) {
    public ProductInfoResponse toResponse() {
        return ProductInfoResponse.builder()
                .productInfoSeq(productInfoSeq)
                .productName(productName)
                .productDetail(productDetail)
                .price(price)
                .productOptions(productOptions)
                .build();
    }
}
