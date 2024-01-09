package com.commerce.core.product.vo;

import com.commerce.core.product.entity.Product;
import com.commerce.core.product.entity.ProductStock;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductStockDto {

    private Long productSeq;
    private Long stock;

    public ProductStock dtoToEntity(Product product) {
        return ProductStock.builder()
                .product(product)
                .stock(stock)
                .build();
    }
}
