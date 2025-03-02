package com.commerce.core.api.response;

import com.commerce.core.product.vo.ProductOptions;
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
}
