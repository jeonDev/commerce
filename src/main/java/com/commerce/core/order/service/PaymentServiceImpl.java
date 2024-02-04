package com.commerce.core.order.service;

import com.commerce.core.order.entity.Orders;
import com.commerce.core.order.repository.OrderDetailsRepository;
import com.commerce.core.order.vo.PaymentDto;
import com.commerce.core.point.service.PointService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentService {

    private final OrderDetailsRepository orderDetailsRepository;

    private final PointService pointService;

    @Transactional
    @Override
    public Orders payment(PaymentDto dto) {
        // 1. 결제 대상 확인

        // 1-1. 결제 금액 계산

        // 2. 결제 처리

        // 3. DB 후 처리
        
        return null;
    }
}
