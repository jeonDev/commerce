package com.commerce.core.product.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Builder
@Getter
public class ProductViewResDto {
    private Long productInfoSeq;
    private String productName;
    private String productDetail;
    private Long price;
    private Long discountPrice;
    private String useYn;
    private String productOption;
}
