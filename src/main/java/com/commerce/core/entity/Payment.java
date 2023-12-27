package com.commerce.core.entity;

import com.commerce.core.vo.common.type.PaymentStatus;
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
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PAYMENT_SEQ")
    private Long paymentSeq;

    @ManyToOne
    @JoinColumn(name = "ORDER_SEQ")
    private Orders orderSeq;

    @Column(name = "PAYMENT_STATUS")
    private PaymentStatus paymentStatus;

}
