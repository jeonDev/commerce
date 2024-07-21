package com.commerce.core.product.repository.dsl.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDAO {
    private Long productSeq;
    private String productOptionCode;
    private Long productInfoSeq;
    private String productName;
    private String productDetail;
    private Long price;
}
