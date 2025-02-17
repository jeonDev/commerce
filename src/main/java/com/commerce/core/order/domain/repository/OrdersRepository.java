package com.commerce.core.order.domain.repository;

import com.commerce.core.order.domain.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
}
