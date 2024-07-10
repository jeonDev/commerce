package com.commerce.core.product.service;

import com.commerce.core.product.entity.Product;
import com.commerce.core.product.entity.ProductInfo;
import com.commerce.core.product.vo.ProductDto;
import com.commerce.core.product.vo.ProductInfoDto;
import com.commerce.core.product.vo.ProductResDto;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    ProductResDto add(ProductDto dto);

    Optional<Product> selectProduct(Long productSeq);

    List<Product> selectProductToProductInfo(Long productInfoSeq);

    /**
     * Product Info Add
     */
    ProductInfo productInfoAdd(ProductDto dto);

    /**
     * Select Product Info
     */
    Optional<ProductInfo> selectProductInfo(Long productInfoSeq);

}
