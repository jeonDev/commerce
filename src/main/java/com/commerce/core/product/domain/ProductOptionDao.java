package com.commerce.core.product.domain;

import com.commerce.core.product.domain.entity.ProductOption;

import java.util.Optional;

public interface ProductOptionDao {
    ProductOption save(ProductOption productOption);
    Optional<ProductOption> findById(Long productOptionSeq);
    Optional<ProductOption> findByIdForUpdate(Long productOptionSeq);
}
