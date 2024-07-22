package com.commerce.core.order.vo;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class OrderDto {
    private Long orderSeq;
    private Long orderDetailSeq;
    @Setter
    private Long memberSeq;
    private OrderStatus orderStatus;
    private BuyProduct[] buyProducts;
}
