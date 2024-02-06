package com.commerce.core.order.entity;

import com.commerce.core.common.entity.BaseEntity;
import com.commerce.core.order.vo.InoutDivisionStatus;
import com.commerce.core.product.entity.Product;
import com.commerce.core.order.vo.OrderStatus;
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
@Table(name = "ORDER_DETAIL")
public class OrderDetail extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_DETAIL_SEQ")
    private Long orderDetailSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_SEQ")
    private Orders orders;

    @ManyToOne(fetch = FetchType.LAZY)
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
     * 납부 금액
     */
    @Column(name = "PAID_AMOUNT")
    private Long paidAmount;

    /**
     * 주문 상태
     */
    @Column(name = "ORDER_STATUS")
    @Enumerated(EnumType.ORDINAL)
    private OrderStatus orderStatus;

    /**
     * History Generator
     */
    public OrderDetailHistory generateHistoryEntity() {
        return OrderDetailHistory.builder()
                .orderDetail(this)
                .orders(orders)
                .product(product)
                .orderStatus(orderStatus)
                .build();
    }

    /**
     * Order Status Update
     */
    public void updateOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    /**
     * 결제 성공 후, 납부 금액에 구매 금액 세팅
     */
    public void paymentSuccessSettingPaidAmount() {
        this.paidAmount = this.buyAmount;
    }

    /**
     * History Generator
     */
    public PaymentHistory generateHistoryEntity(Long point, InoutDivisionStatus status) {
        return PaymentHistory.builder()
                .orderDetail(this)
                .point(point)
                .inoutDivisionStatus(status)
                .build();
    }

}
