package com.commerce.core.payment.entity;

import com.commerce.core.common.entity.BaseEntity;
import com.commerce.core.order.entity.Orders;
import com.commerce.core.payment.vo.PaymentStatus;
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
@Table(name = "PAYMENT")
public class Payment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PAYMENT_SEQ")
    private Long paymentSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_SEQ")
    private Orders orderSeq;

    @Column(name = "PAYMENT_STATUS")
    @Enumerated(EnumType.ORDINAL)
    private PaymentStatus paymentStatus;

    /**
     * 최초 결제 금액
     */
    @Column(name = "INIT_PAYMENT_AMOUNT")
    private Long initPaymentAmount;

    /**
     * 결제 금액
     */
    @Column(name = "PAYMENT_AMOUNT")
    private Long paymentAmount;

    /**
     * 환불 금액
     */
    @Column(name = "REFUND_AMOUNT")
    private Long refundAmount;
}
