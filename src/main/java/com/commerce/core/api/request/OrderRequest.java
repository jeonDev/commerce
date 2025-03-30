package com.commerce.core.api.request;

import com.commerce.core.common.utils.SessionUtils;
import com.commerce.core.order.type.BuyProduct;
import com.commerce.core.order.service.request.OrderServiceRequest;
import com.commerce.core.order.type.OrderStatus;

public record OrderRequest(
        Long orderSeq,
        Long orderDetailSeq,
        OrderStatus orderStatus,
        BuyProduct[] buyProducts
) {

    public OrderServiceRequest toRequest() {
        return OrderServiceRequest.builder()
                .orderSeq(orderSeq)
                .orderDetailSeq(orderDetailSeq)
                .memberSeq(SessionUtils.getMemberSeq())
                .orderStatus(orderStatus)
                .buyProducts(buyProducts)
                .build();
    }
}
