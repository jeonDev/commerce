package com.commerce.core.order.domain;

import com.commerce.core.order.domain.entity.OrderDetail;
import com.commerce.core.order.domain.entity.OrderDetailHistory;
import com.commerce.core.order.domain.entity.Orders;
import com.commerce.core.order.domain.entity.PaymentHistory;
import com.commerce.core.order.domain.entity.mongo.OrderView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface OrderDao {
    Orders save(Orders order);

    void orderDetailSaveAll(List<OrderDetail> orderDetails);
    OrderDetail orderDetailSave(OrderDetail orderDetail);

    void orderDetailHistorySaveAll(List<OrderDetailHistory> orderDetailHistories);
    OrderDetailHistory orderDetailHistorySave(OrderDetailHistory orderDetailHistorie);

    Optional<Orders> findById(Long orderSeq);
    Optional<OrderDetail> orderDetailFindById(Long orderDetailSeq);

    List<OrderDetail> orderDetailListByOrderSeq(Long orderSeq);

    void orderViewSave(OrderView orderView);

    Optional<OrderView> orderViewFindByOrderSeq(Long orderSeq);

    Page<OrderView> orderViewFindAll(Pageable pageable);

    void paymentHistorySave(PaymentHistory paymentHistory);
    void paymentHistorySaveAll(List<PaymentHistory> paymentHistory);

}
