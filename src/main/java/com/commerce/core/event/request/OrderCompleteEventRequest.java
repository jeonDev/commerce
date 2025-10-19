package com.commerce.core.event.request;

public record OrderCompleteEventRequest(
        Long orderSeq,
        Long memberSeq,
        boolean isPayment
) {
}
