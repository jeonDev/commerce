package com.commerce.core.order.vo;

import lombok.Data;

@Data
public class OrderDto {
    private Long orderSeq;
    private Long orderDetailSeq;
    private Long memberSeq;
    private String orderStatus;
    private Long[] productSeqs;

}
