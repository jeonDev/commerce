package com.commerce.core.vo.product;

import com.commerce.core.entity.Product;
import com.commerce.core.entity.ProductStock;
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
