package com.commerce.core.order.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Builder
@Getter
public class OrderViewResDto {
    private Long orderSeq;
    private List<OrderDetailInfo> orderDetailInfos;
    private Long amount;
    private Long buyAmount;
    private Long paidAmount;
    private OrderStatus orderStatus;
}
