package com.commerce.core.entity;

import com.commerce.core.vo.common.type.DeliveryStatus;
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
@Table(name = "DELIVERY")
public class Delivery extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DELIVERY_SEQ")
    private Long deliverySeq;

    @ManyToOne
    @JoinColumn(name = "ORDER_DETAIL_SEQ")
    private OrderDetail orderDetail;

    @Column(name = "DELIVERY_STATUS")
    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

}
