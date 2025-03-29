package com.commerce.core.product.service.response;

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
}
