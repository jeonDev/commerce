package com.commerce.core.vo;

import com.commerce.core.entity.Product;
import com.commerce.core.vo.common.Page;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductDto extends Page {
    private Long productSeq;
    private String productName;

    /**
     * Dto -> Entity
     * @return
     */
    public Product dtoToEntity() {
        return Product.builder()
                .productSeq(productSeq)
                .productName(productName)
                .build();
    }
}
