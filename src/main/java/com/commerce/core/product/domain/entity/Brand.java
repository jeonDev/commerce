package com.commerce.core.product.domain.entity;

import com.commerce.core.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@DynamicUpdate
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "BRAND")
public class Brand extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BRAND_SEQ")
    private Long brandSeq;

    @Column(name = "BRAND_NAME", length = 100)
    private String brandName;

    @Column(name = "DESCRIPTION", length = 150)
    private String description;


}

