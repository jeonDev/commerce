package com.commerce.core.product.service;

import com.commerce.core.product.entity.ProductInfo;
import com.commerce.core.product.vo.ProductInfoDto;

public interface ProductInfoService {

    /**
     * Product Info Add
     * @param dto
     * @return
     */
    ProductInfo add(ProductInfoDto dto);

    /**
     * Select Product Info
     * @param productInfoSeq
     * @return
     */
    ProductInfo selectProductInfo(Long productInfoSeq);
}
