package com.commerce.core.entity;

import com.commerce.core.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Table(name = "PRODUCT_STOCK")
public class ProductStock extends BaseEntity {

    @Id
    @Column(name = "PRODUCT_SEQ")
    private Long productSeq;

    @Column(name = "STOCK")
    private Long stock;

}
