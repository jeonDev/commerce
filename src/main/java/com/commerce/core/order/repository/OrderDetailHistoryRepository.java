package com.commerce.core.order.repository;

import com.commerce.core.order.entity.OrderDetailHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailHistoryRepository extends JpaRepository<OrderDetailHistory, Long> {
}
