package com.commerce.core.order.facade;

import com.commerce.core.order.domain.entity.OrderDetail;
import com.commerce.core.order.service.PaymentService;
import com.commerce.core.point.service.PointService;
import com.commerce.core.point.type.PointProcessStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
public class PaymentFacade {

    private final PaymentService paymentService;
    private final PointService pointService;

    public PaymentFacade(PaymentService paymentService,
                         PointService pointService) {
        this.paymentService = paymentService;
        this.pointService = pointService;
    }

    @Transactional
    public boolean payment(Long orderSeq, Long memberSeq) {
        List<OrderDetail> orderDetailList = paymentService.getOrderDetailList(orderSeq);
        Long paymentAmount = paymentService.getPaymentAmount(orderDetailList);

        pointService.pointAdjustment(memberSeq, paymentAmount, PointProcessStatus.PAYMENT);

        paymentService.paymentSuccessHistorySave(orderDetailList);

        return true;
    }
}
