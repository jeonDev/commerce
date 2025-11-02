package com.commerce.core.product.domain;

import com.commerce.core.product.domain.entity.Product;

import java.util.Optional;

public interface ProductDao {

    Product save(Product product);
    Optional<Product> findById(Long productSeq);

}
