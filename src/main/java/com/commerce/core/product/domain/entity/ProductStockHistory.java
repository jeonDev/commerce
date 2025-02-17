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
}
