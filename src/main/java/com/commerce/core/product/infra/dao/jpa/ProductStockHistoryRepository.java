package com.commerce.core.product.infra.dao.jpa;

import com.commerce.core.product.domain.entity.ProductStockHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductStockHistoryRepository extends JpaRepository<ProductStockHistory, Long> {
}
