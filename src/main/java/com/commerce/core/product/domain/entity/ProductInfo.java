package com.commerce.core.product.domain.entity;

import com.commerce.core.common.entity.BaseEntity;
import com.commerce.core.event.request.ProductEventRequest;
import com.commerce.core.product.type.ProductViewStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 상품 정보
 */
@Entity
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @OneToMany(mappedBy = "productInfo")
    private List<Product> products = new ArrayList<>();


    public static ProductInfo of(
            Long productInfoSeq,
            String productName,
            String productDetail,
            Long price
    ) {
        return new ProductInfo(
                productInfoSeq,
                productName,
                productDetail,
                price,
                null
        );
    }

    public void update(String productName, String productDetail, Long price) {
        this.productName = productName;
        this.productDetail = productDetail;
        this.price = price;
    }

    public ProductEventRequest productMakeEventPublisherRequest(ProductViewStatus status) {
        return new ProductEventRequest(this.productInfoSeq, status);
    }

}
