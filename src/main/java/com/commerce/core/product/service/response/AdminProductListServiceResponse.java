package com.commerce.core.product.service.response;

import com.commerce.core.product.domain.dto.AdminProductListDto;
import lombok.Builder;

@Builder
public record AdminProductListServiceResponse(
        Long productInfoSeq,
        String productName,
        String productDetail,
        Long price
) {
    public static AdminProductListServiceResponse from(AdminProductListDto response) {
        return AdminProductListServiceResponse.builder()
                .productInfoSeq(response.getProductInfoSeq())
                .productName(response.getProductName())
                .productDetail(response.getProductDetail())
                .price(response.getPrice())
                .build();
    }
}
