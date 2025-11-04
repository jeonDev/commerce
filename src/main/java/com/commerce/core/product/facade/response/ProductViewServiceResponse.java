package com.commerce.core.product.facade.response;

import com.commerce.core.product.domain.entity.ProductView;
import com.commerce.core.product.domain.type.ProductOptions;
import com.commerce.core.product.domain.type.ProductStockSummary;
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
    public static ProductViewServiceResponse from(ProductView response) {
        return ProductViewServiceResponse.builder()
                .productInfoSeq(response.getProductInfoSeq())
                .productName(response.getProductName())
                .productDetail(response.getProductDetail())
                .price(response.getPrice())
                .discountPrice(response.getDiscountPrice())
                .useYn(response.getUseYn())
                .productOptions(response.getProductOptions())
                .productStockSummary(response.getProductStockSummary())
                .productStockSummaryName(response.getProductStockSummary().getStatus())
                .build();
    }

}