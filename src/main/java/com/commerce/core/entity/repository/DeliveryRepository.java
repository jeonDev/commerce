package com.commerce.core.entity.repository;

import com.commerce.core.entity.Delivery;
import com.commerce.core.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    Optional<Delivery> findTopByOrderDetailOrderByLastModifiedDtDesc(OrderDetail orderDetail);
}
