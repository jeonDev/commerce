package com.commerce.core.product.domain.entity;

import com.commerce.core.common.exception.CommerceException;
import com.commerce.core.common.exception.ExceptionStatus;
import com.commerce.core.product.type.ProductStockProcessStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "PRODUCT_OPTION")
public class ProductOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_OPTION_SEQ")
    private Long productOptionSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_SEQ")
    private Product product;

    @Column(name = "PRODUCT_OPTION_NAME", length = 100)
    private String productOptionName;

    @Column(name = "DESCRIPTION", length = 150)
    private String description;

    @Column(name = "PRICE")
    private Long price;

    @Column(name = "STOCK")
    private Long stock;

    public static ProductOption of(
            Product product,
            String productOptionName,
            String description,
            Long price,
            Long stock
    ) {
        return new ProductOption(null, product, productOptionName, description, price, stock);
    }

    public ProductStockHistory adjustment(ProductStockProcessStatus productStockProcessStatus, Long stock) {
        if (stock <= 0) {
            throw new CommerceException(ExceptionStatus.VALID_ERROR);
        }

        if (productStockProcessStatus == ProductStockProcessStatus.ADD) {
            this.stock = this.stock + stock;
        } else if (productStockProcessStatus == ProductStockProcessStatus.CONSUME) {
            if (this.stock < stock) {
                throw new CommerceException(ExceptionStatus.SOLD_OUT);
            }
            this.stock = this.stock - stock;
        }

        return ProductStockHistory.of(
                this,
                stock,
                productStockProcessStatus
        );
    }
}
