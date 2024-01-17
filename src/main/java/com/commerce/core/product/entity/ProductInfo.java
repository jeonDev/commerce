package com.commerce.core.product.entity;

import com.commerce.core.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 상품 정보
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Table(name = "PRODUCT_INFO")
public class ProductInfo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_INFO_SEQ")
    private Long productInfoSeq;

    /**
     * 상품명
     */
    @Column(name = "PRODUCT_NAME")
    private String productName;

    /**
     * 상품 설명
     */
    @Column(name = "PRODUCT_DETAIL")
    private String productDetail;

    /**
     * 가격
     */
    @Column(name = "PRICE")
    private Long price;


}
