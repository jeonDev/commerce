package com.commerce.core.product.domain.entity;

import com.commerce.core.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

/**
 * 상품
 */
@Entity
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "PRODUCT")
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_SEQ")
    private Long productSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BRAND_SEQ")
    private Brand brand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_SEQ")
    private Category category;

    @Column(name = "PRODUCT_NAME", length = 150)
    private String productName;

    @Column(name = "DESCRIPTION", length = 200)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private ProductStatus status;

    public static Product of(Brand brand,
                             Category category,
                             String productName,
                             String description,
                             ProductStatus status) {
        return new Product(
                null,
                brand,
                category,
                productName,
                description,
                status
        );
    }

}
