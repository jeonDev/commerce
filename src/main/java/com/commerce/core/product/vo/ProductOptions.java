package com.commerce.core.product.vo;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductOptions {
    private Long productSeq;
    private String productOption;
}
