package com.commerce.core.product.vo;

import com.commerce.core.product.entity.mongo.ProductView;
import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class ProductViewDto {
    private Long productInfoSeq;
    private String productName;
    private String productDetail;
    private Long price;
    private Long discountPrice;
    private String useYn;
    private List<String> productOptions;

    public ProductView dtoToEntity() {
        return ProductView.builder()
                .productInfoSeq(productInfoSeq)
                .productName(productName)
                .productDetail(productDetail)
                .price(price)
                .discountPrice(discountPrice)
                .useYn(useYn)
                .productOptions(productOptions)
                .build();
    }
}
