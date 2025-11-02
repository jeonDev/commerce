package com.commerce.core.product.domain.entity;


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


    public static ProductOption of(
            Product product,
            String productOptionName,
            String description
    ) {
        return new ProductOption(null, product, productOptionName, description);
    }
    // 재고 / 상품 가격 어디서?
    /**
     * # 고려사항
     *  - 옵션이 없는 상품
     *  - 대표 가격 뿌려줘야 하자나.
     *  - 상품별 할인 / 옵션별 할인
     *  - 재고의 경우는 재고를 두고 거기에 Product.id / ProductOption.id 이렇게 두는것도 괜찮을 듯.
     *  - 기타 이벤트
     *   ㄴ 브랜드별 카테고리별 등등 할인..
     *  -
     */
}
