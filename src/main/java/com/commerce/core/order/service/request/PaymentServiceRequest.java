package com.commerce.core.order.service.request;


import lombok.Builder;

@Builder
public record PaymentServiceRequest(
        Long memberSeq,
        Long orderSeq
) {

}
