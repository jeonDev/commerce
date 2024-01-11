package com.commerce.core.product.service;

import com.commerce.core.product.entity.ProductDetail;
import com.commerce.core.product.vo.ProductDetailDto;

import java.util.Optional;

public interface ProductDetailService {
    /**
     * Product Detail Add
     * @param dto
     * @return
     */
    ProductDetail add(ProductDetailDto dto);

    /**
     * Select Product Detail
     * @param productDetailSeq
     * @return
     */
    Optional<ProductDetail> selectProductDetail(Long productDetailSeq);
}
