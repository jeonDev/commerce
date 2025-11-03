package com.commerce.core.product.facade.request;

import com.commerce.core.product.domain.entity.*;
import com.commerce.core.product.domain.type.ProductStatus;
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
            String productOptionDescription,
            Long stock,
            Long price
    ) {
        public ProductOption toEntity(Product product) {
            return ProductOption.of(product, productOptionName, productOptionDescription, price, stock);
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
