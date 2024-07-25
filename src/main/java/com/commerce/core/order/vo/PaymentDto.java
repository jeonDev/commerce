package com.commerce.core.order.vo;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PaymentDto {
    @Setter
    private Long memberSeq;
    private Long orderSeq;

}
