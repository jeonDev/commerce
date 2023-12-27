package com.commerce.core.entity;

import com.commerce.core.vo.product.ProductDto;
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
@Table(name = "PRODUCT")
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_SEQ", length = 20)
    private Long productSeq;

    @Column(name = "PRODUCT_NAME")
    private String productName;

    public ProductDto entityToDto() {
        return ProductDto.builder()
                .productSeq(productSeq)
                .productName(productName)
                .build();
    }
}
