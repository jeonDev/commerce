package com.commerce.core.product.domain.entity;

import com.commerce.core.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 상품
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
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


}
