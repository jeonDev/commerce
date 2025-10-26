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
    @Column(name = "PRODUCT_SEQ", length = 20)
    private Long productSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_INFO_SEQ")
    private ProductInfo productInfo;

    /**
     * 상품 상세 (사이즈 등)
     */
    @Column(name = "PRODUCT_OPTION_CODE")
    private String productOptionCode;

    public static Product of(ProductInfo productInfo,
                             String productOptionCode) {
        return new Product(null, productInfo, productOptionCode);
    }

}
