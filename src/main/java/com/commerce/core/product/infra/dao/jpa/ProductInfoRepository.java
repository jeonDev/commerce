package com.commerce.core.product.infra.dao.jpa;

import com.commerce.core.product.domain.entity.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductInfoRepository extends JpaRepository<ProductInfo, Long> {
}
