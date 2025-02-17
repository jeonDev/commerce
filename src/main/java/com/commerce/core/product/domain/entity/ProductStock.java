package com.commerce.core.product.domain.entity;

import com.commerce.core.common.entity.BaseEntity;
import com.commerce.core.product.vo.ProductStockProcessStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Table(name = "PRODUCT_STOCK")
public class ProductStock extends BaseEntity {

    @Id
    @Column(name = "PRODUCT_SEQ")
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_SEQ")
    private Product product;

    @Column(name = "STOCK")
    private Long stock;

    public ProductStock inventoryAdjustment(Long stock) {
        this.stock += stock;
        return this;
    }

    /**
     * History Entity Generate
     */
    public ProductStockHistory generateHistoryEntity(Long stock, ProductStockProcessStatus productStockProcessStatus) {
        return ProductStockHistory.builder()
                .product(product)
                .stock(stock)
                .productStockProcessStatus(productStockProcessStatus)
                .build();
    }

}
