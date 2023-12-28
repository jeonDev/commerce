package com.commerce.core.entity;

import com.commerce.core.entity.BaseEntity;
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
    @OneToOne
    @JoinColumn(name = "PRODUCT_SEQ")
    private Product product;

    @Column(name = "STOCK")
    private Long stock;

    public void inventoryAdjustment(Long stock) {
        this.stock += stock;
    }

    /**
     * History Entity Generate
     * @return
     */
    public ProductStockHistory generateHistoryEntity() {
        return ProductStockHistory.builder()
                .product(product)
                .stock(stock)
                .build();
    }

}
