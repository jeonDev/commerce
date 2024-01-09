package com.commerce.core.cart.vo;

import lombok.Data;

@Data
public class CartDto {
    private Long productSeq;
    private Long memberSeq;
    private Long productCount;
}
