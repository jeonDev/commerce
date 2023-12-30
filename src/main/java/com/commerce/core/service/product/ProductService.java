package com.commerce.core.service.product;

import com.commerce.core.entity.Product;
import com.commerce.core.vo.product.ProductDto;

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
}
