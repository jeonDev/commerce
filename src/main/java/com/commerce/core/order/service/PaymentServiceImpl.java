package com.commerce.core.order.service;

import com.commerce.core.common.exception.CommerceException;
import com.commerce.core.common.exception.ExceptionStatus;
import com.commerce.core.order.entity.OrderDetail;
import com.commerce.core.order.entity.Orders;
import com.commerce.core.order.repository.OrderDetailsRepository;
import com.commerce.core.order.vo.PaymentDto;
import com.commerce.core.order.vo.PaymentInfoDto;
import com.commerce.core.point.service.PointService;
import com.commerce.core.point.vo.PointDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentService {

    private final OrderDetailsRepository orderDetailsRepository;

    private final OrderService orderService;
    private final PointService pointService;

    @Transactional
    @Override
    public Orders payment(PaymentDto dto) {
        Long payAmount = 0L;
        final List<OrderDetail> orderDetails = new ArrayList<>();

        // 1. 결제 대상 확인
        for(PaymentInfoDto item : dto.getPaymentInfos()) {
            OrderDetail orderDetail = orderService.selectOrderDetail(item.getOrderSeq())
                    .orElseThrow(() -> new CommerceException(ExceptionStatus.ENTITY_IS_EMPTY));

            // 1-1. 결제 금액 계산
            payAmount += orderDetail.getBuyAmount();
            orderDetail.paymentSuccessSettingPaidAmount();
            orderDetails.add(orderDetail);
        }

        // 2. 결제 처리
        PointDto pointDto = new PointDto();
        pointDto.setMemberSeq(dto.getMemberSeq());
        pointDto.setPoint(payAmount);
        pointService.withdraw(pointDto);

        // 3. DB 후 처리
        orderDetailsRepository.saveAll(orderDetails);

        return null;
    }
}
