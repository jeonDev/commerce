package com.commerce.core.order.domain.entity;

import com.commerce.core.common.entity.BaseEntity;
import com.commerce.core.product.domain.entity.Product;
import com.commerce.core.order.type.OrderStatus;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_DETAIL_SEQ")
    private OrderDetail orderDetail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_SEQ")
    private Orders orders;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_SEQ")
    private Product product;

    @Column(name = "CNT")
    private Long cnt;

    /**
     * 주문 상태
     */
    @Column(name = "ORDER_STATUS")
    @Enumerated(EnumType.ORDINAL)
    private OrderStatus orderStatus;

    public static OrderDetailHistory from(OrderDetail orderDetail) {
        return OrderDetailHistory.builder()
                .orderDetail(orderDetail)
                .orders(orderDetail.getOrders())
                .product(orderDetail.getProduct())
                .cnt(orderDetail.getCnt())
                .orderStatus(orderDetail.getOrderStatus())
                .build();
    }

}
