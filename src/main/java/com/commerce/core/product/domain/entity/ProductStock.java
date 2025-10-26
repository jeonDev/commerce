package com.commerce.core.product.domain.entity;

import com.commerce.core.common.entity.BaseEntity;
import com.commerce.core.common.exception.CommerceException;
import com.commerce.core.common.exception.ExceptionStatus;
import com.commerce.core.product.type.ProductStockProcessStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "PRODUCT_STOCK")
public class ProductStock extends BaseEntity {

    private static final Long STOCK_SOLD_OUT_COUNT = 0L;

    @Id
    @Column(name = "PRODUCT_SEQ")
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_SEQ")
    private Product product;

    @Column(name = "STOCK")
    private Long stock;

    public static ProductStock of(Product product, Long stock) {
        return new ProductStock(null, product, stock);
    }

    public void inventoryAdjustment(Long stock) {
        this.stock += stock;
        if (STOCK_SOLD_OUT_COUNT > this.stock) {
            throw new CommerceException(ExceptionStatus.SOLD_OUT);
        }
    }

    public ProductStockHistory generateHistoryEntity(Long stock, ProductStockProcessStatus productStockProcessStatus) {
        return ProductStockHistory.of(product, stock, productStockProcessStatus);
    }

}
