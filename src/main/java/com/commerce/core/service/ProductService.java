package com.commerce.core.service;

import com.commerce.core.entity.Product;
import com.commerce.core.vo.ProductDto;

import java.util.List;

public interface ProductService {
    /**
     * 상품 등록
     * @param dto
     */
    ProductDto add(ProductDto dto);

    /**
     * 상품 조회 (List)
     * @param dto
     * @return
     */
    List<Product> selectProductList(ProductDto dto);

    /**
     * 상품 조회
     * @param dto
     * @return
     */
    ProductDto selectProduct(ProductDto dto);
}
