package com.commerce.core.product.vo;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ProductOrderDto {
    private Long productSeq;
    private String productOptionCode;
    private Long productInfoSeq;
    private String productName;
    private String productDetail;
    private Long price;
}
