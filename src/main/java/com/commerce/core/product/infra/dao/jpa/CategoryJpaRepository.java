package com.commerce.core.product.infra.dao.jpa;

import com.commerce.core.product.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryJpaRepository extends JpaRepository<Category, Long> {
}
