package com.commerce.core.order.domain.entity;

import com.commerce.core.common.entity.BaseEntity;
import com.commerce.core.order.type.OrderDetailInfo;
import com.commerce.core.order.type.OrderStatus;
import com.commerce.core.product.domain.entity.ProductOption;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    @JoinColumn(name = "PRODUCT_OPTION_SEQ")
    private ProductOption productOption;

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


    public static OrderDetail of(Orders order,
                                 ProductOption productOption,
                                 Long cnt) {
        return new OrderDetail(
                null,
                order,
                productOption,
                cnt,
                0L,0L,
                0L,
                OrderStatus.NEW_ORDER
        );
    }

    public void paymentComplete() {
        this.orderStatus = OrderStatus.PAYMENT_COMPLETE;
    }

    public Long getPaymentAmount() {
        return this.getBuyAmount() - this.getPaidAmount();
    }

    public void paymentSuccessSettingPaidAmount(Long paidAmount) {
        this.paidAmount = paidAmount;
    }

    public OrderDetailInfo entityToInfoDto() {
        return OrderDetailInfo.builder()
                .orderDetailSeq(this.orderDetailSeq)
                .productSeq(this.productOption.getProductOptionSeq())
                .cnt(cnt)
//                .productName(this.product.getProductInfo().getProductName())
                .amount(this.amount)
                .buyAmount(this.buyAmount)
                .paidAmount(this.paidAmount)
                .orderStatus(this.orderStatus)
                .build();
    }

}
