package com.commerce.core.product.service;

import com.commerce.core.product.entity.Product;
import com.commerce.core.product.vo.ProductDto;

import java.util.Optional;

public interface ProductService {

    /**
     * Product Add
     */
    Product add(ProductDto dto);

    /**
     * Select Product Detail
     */
    Optional<Product> selectProduct(Long productSeq);

}
