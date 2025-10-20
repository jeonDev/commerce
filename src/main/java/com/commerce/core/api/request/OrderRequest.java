package com.commerce.core.api.request;

import com.commerce.core.common.utils.SessionUtils;
import com.commerce.core.order.type.BuyProduct;
import com.commerce.core.order.service.request.OrderServiceRequest;
import com.commerce.core.order.type.OrderStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

public record OrderRequest(
        @Schema(description = "구매상품내역")
        BuyProduct[] buyProducts,
        @JsonProperty("isPayment")
        @Schema(description = "즉시 결제 여부", example = "true")
        boolean isPayment
) {

    public OrderServiceRequest toRequest() {
        return OrderServiceRequest.builder()
                .memberSeq(SessionUtils.getMemberSeq())
                .buyProducts(buyProducts)
                .isPayment(isPayment)
                .build();
    }
}
