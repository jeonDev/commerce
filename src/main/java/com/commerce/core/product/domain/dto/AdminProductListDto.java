package com.commerce.core.product.domain.dto;

import com.commerce.core.product.service.response.AdminProductListServiceResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminProductListDto {

    private Long productInfoSeq;
    private String productName;
    private String productDetail;
    private Long price;

    public AdminProductListServiceResponse toResponse() {
        return AdminProductListServiceResponse.builder()
                .productInfoSeq(productInfoSeq)
                .productName(productName)
                .productDetail(productDetail)
                .price(price)
                .build();
    }
}
