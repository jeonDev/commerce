package com.commerce.core.product.service;

import com.commerce.core.product.entity.mongo.ProductView;
import com.commerce.core.product.vo.ProductViewDto;
import com.commerce.core.product.vo.ProductViewResDto;

import java.util.List;
import java.util.Optional;

public interface ProductViewService {

    /**
     * Product View Merge
     */
    void merge(ProductViewDto dto);

    /**
     * Select ProductView
     */
    Optional<ProductView> selectProductViewForProductDetail(Long productDetailSeq);

    /**
     * Select ProductView
     */
    Optional<ProductView> selectProductViewDetail(Long productViewSeq);

    List<ProductViewResDto> selectProductViewList();
}