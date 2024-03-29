package com.commerce.core.order.service;

import com.commerce.core.order.entity.OrderDetail;
import com.commerce.core.order.entity.mongo.OrderView;
import com.commerce.core.order.repository.mongo.OrderViewRepository;
import com.commerce.core.order.vo.OrderDetailInfo;
import com.commerce.core.order.vo.OrderViewDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderViewServiceImpl implements OrderViewService {

    private final OrderViewRepository orderViewRepository;

    private final OrderService orderService;

    @Transactional
    @Override
    public void merge(OrderViewDto dto) {
        // 1. Data Setting
        Long buyAmount = 0L;
        Long amount = 0L;
        Long paidAmount = 0L;
        List<OrderDetailInfo> orderDetailInfos = new ArrayList<>();

        // 2. Order Detail Find
        Long orderSeq = dto.getOrderSeq();
        List<OrderDetail> orderDetails = orderService.selectOrderDetailList(orderSeq);

        // 3. Amount Data Setting
        for (OrderDetail orderDetail : orderDetails) {
            buyAmount += orderDetail.getBuyAmount();
            amount += orderDetail.getAmount();
            paidAmount += orderDetail.getPaidAmount();
            orderDetailInfos.add(orderDetail.entityToInfoDto());
        }

        // 4. Order View Data Save
        OrderView orderView;
        Optional<OrderView> optionalOrderView = orderViewRepository.findByOrderSeq(orderSeq);
        if(optionalOrderView.isPresent()) {
            orderView = optionalOrderView.get();
            orderView.settingData(amount, buyAmount, paidAmount, orderDetailInfos);
        } else {
            orderView = OrderView.builder()
                    .orderSeq(orderSeq)
                    .orderDetailInfos(orderDetailInfos)
                    .amount(amount)
                    .buyAmount(buyAmount)
                    .paidAmount(paidAmount)
                    .build();
        }

        orderViewRepository.save(orderView);
    }
}
