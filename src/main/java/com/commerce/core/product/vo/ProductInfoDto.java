package com.commerce.core.product.vo;

import com.commerce.core.product.entity.ProductInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class ProductInfoDto {
    private Long productInfoSeq;
    private String productName;
    private String productDetail;

    public ProductInfo dtoToEntity() {
        return ProductInfo.builder()
                .productInfoSeq(productInfoSeq)
                .productName(productName)
                .productDetail(productDetail)
                .build();
    }
}
