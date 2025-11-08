package com.commerce.core.product.domain;

import com.commerce.core.product.domain.entity.Brand;

import java.util.Optional;

public interface BrandDao {
    Brand save(Brand brand);
    Optional<Brand> findById(Long brandSeq);
}
