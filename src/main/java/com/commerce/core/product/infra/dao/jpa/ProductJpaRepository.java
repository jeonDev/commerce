package com.commerce.core.product.infra.dao.jpa;

import com.commerce.core.product.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpaRepository extends JpaRepository<Product, Long> {
}
