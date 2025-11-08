package com.commerce.core.product.infra.dao.jpa;

import com.commerce.core.product.domain.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandJpaRepository extends JpaRepository<Brand, Long> {
}
