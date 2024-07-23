package com.commerce.core.product.service;

import com.commerce.core.common.vo.PageListVO;
import com.commerce.core.product.entity.mongo.ProductView;
import com.commerce.core.product.vo.ProductDetailDto;
import com.commerce.core.product.vo.ProductOrderDto;
import com.commerce.core.product.vo.ProductViewDto;
import com.commerce.core.product.vo.ProductViewResDto;

import java.util.List;
import java.util.Optional;

public interface ProductViewService {

    void merge(ProductViewDto dto);

    Optional<ProductView> selectProductViewForProductDetail(Long productDetailSeq);

    ProductDetailDto selectProductViewDetail(Long productInfoSeq);
    ProductOrderDto selectProductView(Long productSeq);

    PageListVO<ProductViewResDto> selectProductViewList(int pageNumber, int pageSize);
}