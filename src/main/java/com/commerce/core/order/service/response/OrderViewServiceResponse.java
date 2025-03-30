package com.commerce.core.order.service.response;

import com.commerce.core.order.type.OrderDetailInfo;
import com.commerce.core.order.type.OrderStatus;
import lombok.Builder;

import java.util.List;

@Builder
public record OrderViewServiceResponse(
        Long orderSeq,
        List<OrderDetailInfo> orderDetailInfos,
        Long amount,
        Long buyAmount,
        Long paidAmount,
        OrderStatus orderStatus
) {

}
