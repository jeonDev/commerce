package com.commerce.core.product.vo;

import com.commerce.core.product.entity.ProductInfo;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductInfoDto {
    private Long productInfoSeq;
    private String productName;
    private String productDetail;
    private Long price;

    public ProductInfo dtoToEntity() {
        return ProductInfo.builder()
                .productInfoSeq(productInfoSeq)
                .productName(productName)
                .productDetail(productDetail)
                .price(price)
                .build();
    }
}
