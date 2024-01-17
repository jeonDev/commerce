package com.commerce.core.product.vo;

import com.commerce.core.product.entity.mongo.ProductView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProductViewDto {
    private Long productViewSeq;
    private Long productInfoSeq;
    private String productName;
    private String productDetail;
    private Long price;
    private Long discountPrice;
    private String useYn;
    private List<String> productOptions;
    private List<String> productDetailCodes;

    public ProductView dtoToEntity() {
        return ProductView.builder()
                .productViewSeq(productViewSeq)
                .productInfoSeq(productInfoSeq)
                .productName(productName)
                .productDetail(productDetail)
                .price(price)
                .discountPrice(discountPrice)
                .useYn(useYn)
                .productOptions(productOptions)
                .productDetailCodes(productDetailCodes)
                .build();
    }
}
