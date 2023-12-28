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
@Table(name = "ORDER_DETAIL_HISTORY")
public class OrderDetailHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_DETAIL_HISTORY_SEQ")
    private Long orderDetailHistorySeq;

    @ManyToOne
    @JoinColumn(name = "ORDER_SEQ")
    private Orders orders;

    @OneToOne
    @JoinColumn(name = "PRODUCT_SEQ")
    private Product product;

    /**
     * 주문 상태
     */
    @Column(name = "ORDER_STATUS")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

}
