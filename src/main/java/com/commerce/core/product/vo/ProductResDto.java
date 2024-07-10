package com.commerce.core.product.vo;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class ProductResDto {
    private Long productInfoSeq;
    private String productName;
    private String productDetail;
    private Long price;
}
