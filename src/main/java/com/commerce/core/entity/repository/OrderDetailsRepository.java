package com.commerce.core.entity.repository;

import com.commerce.core.entity.OrderDetail;
import com.commerce.core.entity.OrderDetailsId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailsRepository extends JpaRepository<OrderDetail, OrderDetailsId> {
}
