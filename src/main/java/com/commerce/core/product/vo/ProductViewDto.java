package com.commerce.core.product.vo;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class ProductViewDto {
    private Long productSeq;
    private ProductViewStatus productViewStatus;


    @Getter
    public enum ProductViewStatus {
        REGISTER, STOCK_ADJUSTMENT
    }
}
