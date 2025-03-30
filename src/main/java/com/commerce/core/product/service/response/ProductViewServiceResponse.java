package com.commerce.core.product.service.response;

import com.commerce.core.product.domain.entity.mongo.ProductView;
import com.commerce.core.product.type.ProductOptions;
import com.commerce.core.product.type.ProductStockSummary;
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