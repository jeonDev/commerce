package com.commerce.core.product.domain.repository;

import com.commerce.core.product.domain.entity.ProductStockHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductStockHistoryRepository extends JpaRepository<ProductStockHistory, Long> {
}
