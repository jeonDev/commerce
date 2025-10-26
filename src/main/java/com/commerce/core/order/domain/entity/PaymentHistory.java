package com.commerce.core.order.domain.entity;

import com.commerce.core.common.entity.BaseEntity;
import com.commerce.core.order.type.InoutDivisionStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "PAYMENT_HISTORY")
public class PaymentHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PAYMENT_HISTORY_SEQ")
    private Long paymentHistorySeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_DETAIL_SEQ")
    private OrderDetail orderDetail;

    @Column(name = "price")
    private Long point;

    @Column(name = "INOUT_DIVISION_STATUS")
    @Enumerated(EnumType.ORDINAL)
    private InoutDivisionStatus inoutDivisionStatus;

    public static PaymentHistory from(OrderDetail orderDetail, Long point, InoutDivisionStatus status) {
        return new PaymentHistory(
                null,
                orderDetail,
                point,
                status
        );
    }
}
