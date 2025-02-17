package com.commerce.core.product.domain.repository;

import com.commerce.core.product.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByProductInfo_ProductInfoSeq(Long productInfoSeq);
}
