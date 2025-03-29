package com.commerce.core.product.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminProductListDto {

    private Long productInfoSeq;
    private String productName;
    private String productDetail;
    private Long price;

}
