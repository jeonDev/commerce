package com.commerce.core.product.vo;

import com.commerce.core.product.entity.ProductDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class ProductDetailDto {
    private Long productDetailSeq;
    private String productDetailCode;

    public ProductDetail dtoToEntity() {
        return ProductDetail.builder()
                .productDetailSeq(productDetailSeq)
                .productDetailCode(productDetailCode)
                .build();
    }
}
