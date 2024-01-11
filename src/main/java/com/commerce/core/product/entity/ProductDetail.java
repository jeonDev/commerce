package com.commerce.core.product.entity;

import com.commerce.core.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 상품 상세 구분
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Table(name = "PRODUCT_DETAIL")
public class ProductDetail extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_DETAIL_SEQ")
    private Long productDetailSeq;

    /**
     * 상품 상세 구분 (색상 등)
     */
    @Column(name = "PRODUCT_DETAIL_CODE")
    private String productDetailCode;
}
