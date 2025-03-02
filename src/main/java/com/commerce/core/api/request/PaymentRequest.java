package com.commerce.core.api.request;

import com.commerce.core.common.utils.SessionUtils;
import com.commerce.core.order.service.request.PaymentServiceRequest;

public record PaymentRequest(
        Long orderSeq
) {

    public PaymentServiceRequest toRequest() {
        return PaymentServiceRequest.builder()
                .memberSeq(SessionUtils.getMemberSeq())
                .orderSeq(orderSeq)
                .build();
    }
}
