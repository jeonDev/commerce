package com.commerce.core.order.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetailInfo {
    private Long orderDetailSeq;
    private Long productSeq;
    private String productName;
    private Long amount;
    private Long buyAmount;
    private Long paidAmount;
    private OrderStatus orderStatus;
}
