package com.commerce.core.order.service.request;

import com.commerce.core.order.type.BuyProduct;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
public record OrderServiceRequest(
        Long memberSeq,
        BuyProduct[] buyProducts,
        @JsonProperty("isPayment")
        boolean isPayment
) {

}
