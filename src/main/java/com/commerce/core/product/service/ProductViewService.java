package com.commerce.core.product.service;

import com.commerce.core.common.vo.PageListVO;
import com.commerce.core.product.domain.entity.mongo.ProductView;
import com.commerce.core.product.service.request.ProductViewServiceRequest;
import com.commerce.core.product.domain.dto.AdminProductListDto;
import com.commerce.core.product.service.response.AdminProductListServiceResponse;
import com.commerce.core.product.service.response.ProductDetailServiceResponse;
import com.commerce.core.product.service.response.ProductOrderServiceResponse;
import com.commerce.core.product.service.response.ProductViewServiceResponse;

import java.util.Optional;

public interface ProductViewService {

    void merge(ProductViewServiceRequest request);
    Optional<ProductView> selectProductViewForProductDetail(Long productDetailSeq);
    ProductDetailServiceResponse selectProductViewDetail(Long productInfoSeq);
    ProductOrderServiceResponse selectProductView(Long productSeq);
    PageListVO<ProductViewServiceResponse> selectProductViewList(int pageNumber, int pageSize);
    PageListVO<AdminProductListServiceResponse> selectProductList(int pageNumber, int pageSize);
}