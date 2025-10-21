package com.commerce.core.order.domain;

import com.commerce.core.order.domain.entity.OrderDetail;
import com.commerce.core.order.domain.entity.OrderDetailHistory;
import com.commerce.core.order.domain.entity.Orders;
import com.commerce.core.order.domain.entity.PaymentHistory;
import com.commerce.core.order.domain.entity.mongo.OrderView;
import com.commerce.core.order.domain.repository.OrderDetailHistoryRepository;
import com.commerce.core.order.domain.repository.OrderDetailsRepository;
import com.commerce.core.order.domain.repository.OrdersRepository;
import com.commerce.core.order.domain.repository.PaymentHistoryRepository;
import com.commerce.core.order.domain.repository.mongo.OrderViewRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class OrderDaoImpl implements OrderDao {

    private final OrdersRepository ordersRepository;
    private final OrderDetailsRepository orderDetailsRepository;
    private final OrderDetailHistoryRepository orderDetailHistoryRepository;
    private final PaymentHistoryRepository paymentHistoryRepository;
    private final OrderViewRepository orderViewRepository;

    public OrderDaoImpl(OrdersRepository ordersRepository,
                        OrderDetailsRepository orderDetailsRepository,
                        OrderDetailHistoryRepository orderDetailHistoryRepository,
                        PaymentHistoryRepository paymentHistoryRepository,
                        OrderViewRepository orderViewRepository) {
        this.ordersRepository = ordersRepository;
        this.orderDetailsRepository = orderDetailsRepository;
        this.orderDetailHistoryRepository = orderDetailHistoryRepository;
        this.paymentHistoryRepository = paymentHistoryRepository;
        this.orderViewRepository = orderViewRepository;
    }

    @Override
    public Orders save(Orders order) {
        return ordersRepository.save(order);
    }

    @Override
    public void orderDetailSaveAll(List<OrderDetail> orderDetails) {
        orderDetailsRepository.saveAll(orderDetails);
    }

    @Override
    public void orderDetailHistorySaveAll(List<OrderDetailHistory> orderDetailHistories) {
        orderDetailHistoryRepository.saveAll(orderDetailHistories);
    }

    @Override
    public Optional<Orders> findById(Long orderSeq) {
        return ordersRepository.findById(orderSeq);
    }

    @Override
    public List<OrderDetail> orderDetailListByOrderSeq(Long orderSeq) {
        return orderDetailsRepository.findByOrders_OrderSeq(orderSeq);
    }

    @Override
    public void orderViewSave(OrderView orderView) {
        orderViewRepository.save(orderView);
    }

    @Override
    public Optional<OrderView> orderViewFindByOrderSeq(Long orderSeq) {
        return orderViewRepository.findByOrderSeq(orderSeq);
    }

    @Override
    public Page<OrderView> orderViewFindAll(Pageable pageable) {
        return orderViewRepository.findAll(pageable);
    }

    @Override
    public void paymentHistorySaveAll(List<PaymentHistory> paymentHistory) {
        paymentHistoryRepository.saveAll(paymentHistory);
    }
}
