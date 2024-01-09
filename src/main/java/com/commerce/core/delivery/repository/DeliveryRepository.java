package com.commerce.core.delivery.repository;

import com.commerce.core.delivery.entity.Delivery;
import com.commerce.core.order.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    Optional<Delivery> findTopByOrderDetailOrderByLastModifiedDtDesc(OrderDetail orderDetail);
}
