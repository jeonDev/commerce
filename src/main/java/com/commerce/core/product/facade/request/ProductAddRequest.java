package com.commerce.core.product.facade.request;

import com.commerce.core.product.domain.entity.*;
import lombok.Builder;

import java.util.List;

@Builder
public record ProductAddRequest(
        Long brandSeq,
        Long categorySeq,
        String productName,
        String productDescription,
        Long price,
        Long stock,
        List<ProductOptionRequest> productOptionRequestList
) {

    @Builder
    public record ProductOptionRequest(
            String productOptionName,
            String productOptionDescription
    ) {
        public ProductOption toEntity(Product product) {
            return ProductOption.of(product, productOptionName, productOptionDescription);
        }
    }

    public Product toEntity(Brand brand, Category category) {
        // TODO: 가격 / 재고 어디?
        return Product.of(
                brand,
                category,
                productName,
                productDescription,
                ProductStatus.ACTIVE
        );
    }
}
