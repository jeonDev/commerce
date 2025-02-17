package com.commerce.core.product.vo;

import com.commerce.core.product.domain.entity.ProductInfo;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class ProductDto {

    private Long productInfoSeq;
    @NotNull
    private String productName;
    @NotNull
    private String productDetail;
    @Min(0)
    @NotNull
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
