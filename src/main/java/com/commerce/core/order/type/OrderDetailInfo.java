package com.commerce.core.order.type;

import lombok.Builder;

@Builder
public record OrderDetailInfo(
        Long orderDetailSeq,
        Long productSeq,
        Long cnt,
        String productName,
        Long amount,
        Long buyAmount,
        Long paidAmount,
        OrderStatus orderStatus
) {

}
