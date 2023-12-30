package com.commerce.core.vo.cart;

import lombok.Data;

@Data
public class CartDto {
    private Long productSeq;
    private Long memberSeq;
    private Long productCount;
}
