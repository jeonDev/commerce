package com.commerce.core.order.service.request;

import com.commerce.core.order.vo.BuyProduct;
import com.commerce.core.order.vo.OrderStatus;
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
        boolean payment
) {

}
