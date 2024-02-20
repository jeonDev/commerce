package com.commerce.core.product.vo;

import com.commerce.core.product.entity.Product;
import com.commerce.core.product.entity.ProductInfo;
import lombok.Data;

@Data
public class ProductDto {
    private Long productSeq;
    private String productName;
    private String productOptionCode;
    private ProductInfoDto productInfoDto;

    /**
     * Dto -> Entity
     */
    public Product dtoToEntity(ProductInfo productInfo) {
        return Product.builder()
                .productSeq(productSeq)
                .productInfo(productInfo)
                .productOptionCode(productOptionCode)
                .build();
    }
}
