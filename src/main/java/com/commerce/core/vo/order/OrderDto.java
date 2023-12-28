package com.commerce.core.vo.order;

import lombok.Data;

@Data
public class OrderDto {
    private Long orderSeq;
    private Long memberSeq;
    private String orderStatus;
    private Long[] productSeqs;

}
