package com.commerce.core.entity.repository;

import com.commerce.core.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailsRepository extends JpaRepository<OrderDetail, Long> {
    List<OrderDetail> findByOrders_OrderSeq(Long orderSeq);
}
