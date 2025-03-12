package com.commerce.core.product.service.response;

import com.commerce.core.product.vo.ProductOptions;
import com.commerce.core.product.vo.ProductStockSummary;
import lombok.Builder;

import java.util.List;

@Builder
public record ProductViewServiceResponse (
        Long productInfoSeq,
        String productName,
        String productDetail,
        Long price,
        Long discountPrice,
        String useYn,
        List<ProductOptions> productOptions,
        ProductStockSummary productStockSummary,
        String productStockSummaryName
) {

}