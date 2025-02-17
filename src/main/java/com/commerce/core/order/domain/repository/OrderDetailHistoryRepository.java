package com.commerce.core.order.domain.repository;

import com.commerce.core.order.domain.entity.OrderDetailHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailHistoryRepository extends JpaRepository<OrderDetailHistory, Long> {
}
