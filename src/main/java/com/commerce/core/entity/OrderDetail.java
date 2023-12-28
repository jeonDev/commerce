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
@IdClass(OrderDetailsId.class)
@Table(name = "ORDER_DETAIL")
public class OrderDetail extends BaseEntity {

    @Id
    @ManyToOne
    @JoinColumn(name = "ORDER_SEQ")
    private Orders orders;

    @Id
    @OneToOne
    @JoinColumn(name = "PRODUCT_SEQ")
    private Product product;

    /**
     * 실제 금액
     */
    @Column(name = "AMOUNT")
    private Long amount;

    /**
     * 구매 금액
     */
    @Column(name = "BUY_AMOUNT")
    private Long buyAmount;

    /**
     * 주문 상태
     */
    @Column(name = "ORDER_STATUS")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    public OrderDetailHistory generateHistoryEntity() {
        return OrderDetailHistory.builder()
                .orders(orders)
                .product(product)
                .orderStatus(orderStatus)
                .build();
    }
}
