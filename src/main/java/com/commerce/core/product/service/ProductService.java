package com.commerce.core.product.service;

import com.commerce.core.product.entity.Product;
import com.commerce.core.product.vo.ProductDto;

import java.util.List;

public interface ProductService {

    /**
     * Product Add
     * @param dto
     */
    Product add(ProductDto dto);

    /**
     * Select Product List
     * @param dto
     * @return
     */
    List<Product> selectProductList(ProductDto dto);

    /**
     * Select Product Detail
     * @param dto
     * @return
     */
    Product selectProduct(Long productSeq);

    /**
     * Select Sales Products
     * TODO: nosql로 상품 정보 조회
     */
    List<?> selectSalesProducts();

}
