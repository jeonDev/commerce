package com.commerce.core.order.service.response;

import com.commerce.core.order.vo.OrderDetailInfo;
import com.commerce.core.order.vo.OrderStatus;
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
