package com.commerce.core.product.infra.dao.jpa;

import com.commerce.core.product.domain.entity.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOptionJpaRepository extends JpaRepository<ProductOption, Long> {
}
