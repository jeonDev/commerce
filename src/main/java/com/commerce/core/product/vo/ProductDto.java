package com.commerce.core.product.vo;

import com.commerce.core.product.entity.ProductInfo;
import lombok.Data;

import java.util.List;

@Data
public class ProductDto {

    private Long productInfoSeq;
    private String productName;
    private String productDetail;
    private Long price;
    private List<String> productOptions;

    /**
     * Dto -> Entity
     */
    public ProductInfo dtoToEntity() {
        return ProductInfo.builder()
                .productInfoSeq(this.productInfoSeq)
                .productName(this.productName)
                .productDetail(this.productDetail)
                .price(this.price)
                .build();
    }
}
