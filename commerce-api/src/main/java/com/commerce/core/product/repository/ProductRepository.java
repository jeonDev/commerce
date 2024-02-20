package com.commerce.core.product.repository;

import com.commerce.core.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByProductInfo_ProductInfoSeq(Long productInfoSeq);
}
