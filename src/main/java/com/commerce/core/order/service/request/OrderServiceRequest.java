package com.commerce.core.order.service.request;

import com.commerce.core.order.type.BuyProduct;
import com.commerce.core.order.type.OrderStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
public record OrderServiceRequest(
        Long orderSeq,
        Long orderDetailSeq,
        Long memberSeq,
        OrderStatus orderStatus,
        BuyProduct[] buyProducts,
        @JsonProperty("isPayment")
        boolean isPayment
) {

}
