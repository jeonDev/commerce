package com.commerce.core.order.service;

import com.commerce.core.common.vo.PageListVO;
import com.commerce.core.order.entity.OrderDetail;
import com.commerce.core.order.entity.mongo.OrderView;
import com.commerce.core.order.repository.mongo.OrderViewRepository;
import com.commerce.core.order.vo.OrderDetailInfo;
import com.commerce.core.order.vo.OrderStatus;
import com.commerce.core.order.vo.OrderViewDto;
import com.commerce.core.order.vo.OrderViewResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        log.debug(dto.toString());
        // 1. Data Setting
        Long buyAmount = 0L;
        Long amount = 0L;
        Long paidAmount = 0L;
        boolean isPaymentComplete = false;
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
            if (OrderStatus.PAYMENT_COMPLETE == orderDetail.getOrderStatus()) isPaymentComplete = true;
        }

        OrderStatus orderStatus = isPaymentComplete ? OrderStatus.PAYMENT_COMPLETE : OrderStatus.NEW_ORDER;

        // 4. Order View Data Save
        OrderView orderView;
        Optional<OrderView> optionalOrderView = orderViewRepository.findByOrderSeq(orderSeq);
        if(optionalOrderView.isPresent()) {
            orderView = optionalOrderView.get();
            orderView.settingData(amount, buyAmount, paidAmount, orderDetailInfos, orderStatus);
        } else {
            orderView = OrderView.builder()
                    .orderSeq(orderSeq)
                    .orderDetailInfos(orderDetailInfos)
                    .amount(amount)
                    .buyAmount(buyAmount)
                    .paidAmount(paidAmount)
                    .orderStatus(orderStatus)
                    .build();
        }

        orderViewRepository.save(orderView);
    }

    @Transactional(readOnly = true)
    @Override
    public PageListVO<OrderViewResDto> selectOrderView(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<OrderView> list = orderViewRepository.findAll(pageable);
        return PageListVO.<OrderViewResDto>builder()
                .list(list.getContent().stream()
                        .map(OrderView::documentToResDto)
                        .toList()
                )
                .totalPage(list.getTotalPages())
                .build();
    }
}
