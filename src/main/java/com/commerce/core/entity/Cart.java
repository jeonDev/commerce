package com.commerce.core.entity;

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
@Table(name = "CART")
public class Cart extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CARD_SEQ")
    private Long cartSeq;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_SEQ")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "MEMBER_SEQ")
    private Member member;

}
