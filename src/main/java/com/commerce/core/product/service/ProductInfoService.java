package com.commerce.core.product.service;

import com.commerce.core.product.entity.ProductInfo;
import com.commerce.core.product.vo.ProductInfoDto;

import java.util.Optional;

public interface ProductInfoService {

    /**
     * Product Info Add
     */
    ProductInfo add(ProductInfoDto dto);

    /**
     * Select Product Info
     */
    Optional<ProductInfo> selectProductInfo(Long productInfoSeq);
}
