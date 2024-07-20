package com.commerce.core.product.vo;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductStockDto {
    @NotNull
    private Long productSeq;
    @Min(0)
    @NotNull
    private Long stock;
    @NotNull
    private ProductStockProcessStatus productStockProcessStatus;
}
