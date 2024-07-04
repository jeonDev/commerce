package com.commerce.core.product.service;

import com.commerce.core.product.entity.Product;
import com.commerce.core.product.entity.ProductInfo;
import com.commerce.core.product.vo.ProductDto;
import com.commerce.core.product.vo.ProductInfoDto;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Product add(ProductDto dto);

    Optional<Product> selectProduct(Long productSeq);

    List<Product> selectProductToProductInfo(Long productInfoSeq);

    /**
     * Product Info Add
     */
    ProductInfo productInfoAdd(ProductInfoDto dto);

    /**
     * Select Product Info
     */
    Optional<ProductInfo> selectProductInfo(Long productInfoSeq);

}
