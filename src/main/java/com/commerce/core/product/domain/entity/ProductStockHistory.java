package com.commerce.core.product.domain.entity;

import com.commerce.core.common.entity.BaseEntity;
import com.commerce.core.product.type.ProductStockProcessStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "PRODUCT_STOCK_HISTORY")
public class ProductStockHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_HISTORY_SEQ")
    private Long productHistorySeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_SEQ")
    private Product product;

    @Column(name = "STOCK")
    private Long stock;

    @Column(name = "PRODUCT_STOCK_PROCESS_STATUS")
    @Enumerated(EnumType.ORDINAL)
    private ProductStockProcessStatus productStockProcessStatus;

    public static ProductStockHistory of(
            Product product,
            Long stock,
            ProductStockProcessStatus productStockProcessStatus
    ) {
        return new ProductStockHistory(
                null,
                product,
                stock,
                productStockProcessStatus
        );
    }
}
