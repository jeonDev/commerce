package com.commerce.core.order.vo;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OrderDto {
    private Long orderSeq;
    private Long orderDetailSeq;
    private Long memberSeq;
    private OrderStatus orderStatus;
    private Long[] productSeqs;
}
