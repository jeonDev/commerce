package com.commerce.core.order.service.request;

import lombok.*;

@Builder
public record OrderViewMergeServiceRequest(
        Long orderSeq
) {
}
