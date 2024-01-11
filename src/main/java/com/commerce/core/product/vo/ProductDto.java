package com.commerce.core.product.vo;

import com.commerce.core.product.entity.Product;
import com.commerce.core.common.vo.PageDto;
import lombok.Data;

@Data
public class ProductDto extends PageDto {
    private Long productSeq;
    private String productName;

    /**
     * Dto -> Entity
     * @return
     */
    public Product dtoToEntity() {
        return Product.builder()
                .productSeq(productSeq)
                .build();
    }
}
