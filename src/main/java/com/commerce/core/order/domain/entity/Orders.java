package com.commerce.core.order.domain.entity;

import com.commerce.core.common.entity.BaseEntity;
import com.commerce.core.event.request.OrderCompleteEventRequest;
import com.commerce.core.member.domain.entity.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "ORDERS")
public class Orders extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_SEQ")
    private Long orderSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_SEQ")
    private Member member;

    public static Orders of(Member member) {
        return new Orders(null, member);
    }

    public OrderCompleteEventRequest orderCompleteMakeEventPublisherRequest(boolean isPayment) {
        return new OrderCompleteEventRequest(this.orderSeq,
                this.member.getMemberSeq(),
                isPayment
        );
    }
}
