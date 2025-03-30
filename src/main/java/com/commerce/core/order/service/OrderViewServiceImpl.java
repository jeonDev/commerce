package com.commerce.core.order.service;

import com.commerce.core.common.type.PageListResponse;
import com.commerce.core.order.domain.OrderDao;
import com.commerce.core.order.domain.entity.OrderDetail;
import com.commerce.core.order.domain.entity.mongo.OrderView;
import com.commerce.core.order.service.response.OrderViewServiceResponse;
import com.commerce.core.order.type.OrderDetailInfo;
import com.commerce.core.order.type.OrderStatus;
import com.commerce.core.order.service.request.OrderViewMergeServiceRequest;
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

    private final OrderDao orderDao;
    private final OrderService orderService;

    @Transactional
    @Override
    public void merge(OrderViewMergeServiceRequest request) {
        log.debug(request.toString());
        // 1. Data Setting
        Long buyAmount = 0L;
        Long amount = 0L;
        Long paidAmount = 0L;
        boolean isPaymentComplete = false;
        List<OrderDetailInfo> orderDetailInfos = new ArrayList<>();

        // 2. Order Detail Find
        Long orderSeq = request.orderSeq();
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
        Optional<OrderView> optionalOrderView = orderDao.orderViewFindByOrderSeq(orderSeq);
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

        orderDao.orderViewSave(orderView);
    }

    @Transactional(readOnly = true)
    @Override
    public PageListResponse<OrderViewServiceResponse> selectOrderView(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<OrderView> list = orderDao.orderViewFindAll(pageable);
        return PageListResponse.<OrderViewServiceResponse>builder()
                .list(list.getContent().stream()
                        .map(OrderView::documentToResDto)
                        .toList()
                )
                .totalPage(list.getTotalPages())
                .build();
    }
}
