package com.commerce.core.order.domain.entity;

import com.commerce.core.common.entity.BaseEntity;
import com.commerce.core.order.vo.InoutDivisionStatus;
import com.commerce.core.order.vo.OrderDetailInfo;
import com.commerce.core.product.domain.entity.Product;
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

    @Column(name = "CNT", nullable = false)
    private Long cnt;

    /**
     * 실제 금액
     */
    @Column(name = "AMOUNT", nullable = false)
    private Long amount;

    /**
     * 구매 금액
     */
    @Column(name = "BUY_AMOUNT", nullable = false)
    private Long buyAmount;

    /**
     * 납부 금액
     */
    @Column(name = "PAID_AMOUNT", nullable = false)
    private Long paidAmount;

    /**
     * 주문 상태
     */
    @Column(name = "ORDER_STATUS")
    @Enumerated(EnumType.ORDINAL)
    private OrderStatus orderStatus;

    /**
     * Order Status Update
     */
    public void updateOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    /**
     * 결제 성공 후, 납부 금액에 구매 금액 세팅
     */
    public void paymentSuccessSettingPaidAmount(Long paidAmount) {
        this.paidAmount = paidAmount;
    }

    public OrderDetailInfo entityToInfoDto() {
        return OrderDetailInfo.builder()
                .orderDetailSeq(this.orderDetailSeq)
                .productSeq(this.product.getProductSeq())
                .cnt(cnt)
                .productName(this.product.getProductInfo().getProductName())
                .amount(this.amount)
                .buyAmount(this.buyAmount)
                .paidAmount(this.paidAmount)
                .orderStatus(this.orderStatus)
                .build();
    }

}
