package com.commerce.core.api.response;

import com.commerce.core.product.service.response.ProductDetailServiceResponse;
import com.commerce.core.product.domain.type.ProductOptions;
import lombok.Builder;

import java.util.List;

@Builder
public record ProductInfoResponse(
        Long productInfoSeq,
        String productName,
        String productDetail,
        Long price,
        List<ProductOptions> productOptions
) {
    public static ProductInfoResponse from(ProductDetailServiceResponse response) {
        return ProductInfoResponse.builder()
                .productInfoSeq(response.productInfoSeq())
                .productName(response.productName())
                .productDetail(response.productDetail())
                .price(response.price())
                .productOptions(response.productOptions())
                .build();
    }
}
