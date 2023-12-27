package com.commerce.core.entity;

import com.commerce.core.vo.common.type.OrderStatus;
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
@Table(name = "ORDERS")
public class Orders extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_SEQ")
    private Long orderSeq;

    @ManyToOne
    @JoinColumn(name = "MEMBER_SEQ")
    private Member member;

    @Column(name = "ORDER_STATUS")
    private OrderStatus orderStatus;


}
