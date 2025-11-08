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
@Table(name = "CATEGORY")
public class Category extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CATEGORY_SEQ")
    private Long categorySeq;

    @Column(name = "UPPER_CATEGORY_LEVEL")
    private Long upperCategoryLevel;

    @Column(name = "CATEGORY_LEVEL")
    private Integer categoryLevel;

    @Column(name = "CATEGORY_NAME", length = 75)
    private String categoryName;

    @Column(name = "SORT_ORDER")
    private Integer sortOrder;

}
