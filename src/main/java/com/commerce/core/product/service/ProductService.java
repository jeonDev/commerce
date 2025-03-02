package com.commerce.core.product.service;

import com.commerce.core.product.domain.entity.Product;
import com.commerce.core.product.domain.entity.ProductInfo;
import com.commerce.core.product.service.request.ProductServiceRequest;
import com.commerce.core.product.service.response.ProductServiceResponse;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    ProductServiceResponse add(ProductServiceRequest request);

    Optional<Product> selectProduct(Long productSeq);

    List<Product> selectProductToProductInfo(Long productInfoSeq);

    /**
     * Product Info Add
     */
    ProductInfo productInfoAdd(ProductServiceRequest request);

    /**
     * Select Product Info
     */
    Optional<ProductInfo> selectProductInfo(Long productInfoSeq);

}
