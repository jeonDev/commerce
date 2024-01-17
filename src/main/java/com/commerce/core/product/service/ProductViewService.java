package com.commerce.core.product.service;

import com.commerce.core.product.entity.mongo.ProductView;
import com.commerce.core.product.vo.ProductViewDto;

import java.util.Optional;

public interface ProductViewService {

    /**
     * Product View Register
     * @param dto
     * @return
     */
    ProductView register(ProductViewDto dto);

    /**
     * Product View Update
     * @param dto
     * @return
     */
    ProductView update(ProductViewDto dto);

    /**
     * Select ProductView
     * @param productDetailSeq
     * @return
     */
    Optional<ProductView> selectProductViewForProductDetail(Long productDetailSeq);

    /**
     * Select ProductView
     * @param productViewSeq
     * @return
     */
    Optional<ProductView> selectProductView(Long productViewSeq);
}
