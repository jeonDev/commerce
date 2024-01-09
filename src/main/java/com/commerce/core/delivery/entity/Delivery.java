package com.commerce.core.delivery.entity;

import com.commerce.core.common.entity.BaseEntity;
import com.commerce.core.order.entity.OrderDetail;
import com.commerce.core.delivery.vo.DeliveryStatus;
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
public class Delivery extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DELIVERY_SEQ")
    private Long deliverySeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_DETAIL_SEQ")
    private OrderDetail orderDetail;

    @Column(name = "DELIVERY_STATUS")
    @Enumerated(EnumType.ORDINAL)
    private DeliveryStatus deliveryStatus;

}
