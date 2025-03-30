package com.commerce.core.product.domain.repository.dsl.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {
    private Long productSeq;
    private String productOptionCode;
    private Long productInfoSeq;
    private String productName;
    private String productDetail;
    private Long price;
}
