package com.commerce.core.product.service;

import com.commerce.core.product.entity.Product;
import com.commerce.core.product.vo.ProductDto;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Product add(ProductDto dto);

    Optional<Product> selectProduct(Long productSeq);

    List<Product> selectProductToProductInfo(Long productInfoSeq);

}
