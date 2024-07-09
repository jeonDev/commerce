package com.commerce.core.product.vo;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ProductDetailDto {
    private Long productInfoSeq;
    private String productName;
    private String productDetail;
    private Long price;
}
