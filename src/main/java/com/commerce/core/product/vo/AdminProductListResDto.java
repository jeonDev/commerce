package com.commerce.core.product.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminProductListResDto {

    private Long productInfoSeq;
    private String productName;
    private String productDetail;
    private Long price;
}
