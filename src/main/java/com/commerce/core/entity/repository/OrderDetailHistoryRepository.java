package com.commerce.core.entity.repository;

import com.commerce.core.entity.OrderDetailHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailHistoryRepository extends JpaRepository<OrderDetailHistory, Long> {
}
