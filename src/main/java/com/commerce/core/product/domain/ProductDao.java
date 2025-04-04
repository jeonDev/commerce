package com.commerce.core.product.domain;

import com.commerce.core.product.domain.entity.Product;
import com.commerce.core.product.domain.entity.ProductInfo;
import com.commerce.core.product.domain.entity.mongo.ProductView;
import com.commerce.core.product.domain.repository.dsl.dto.ProductDto;
import com.commerce.core.product.domain.dto.AdminProductListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductDao {
    List<Product> saveAll(List<Product> list);

    Optional<Product> findById(Long productSeq);

    List<Product> findByProductInfoSeq(Long productInfoSeq);

    ProductInfo productInfoSave(ProductInfo productInfo);

    Optional<ProductInfo> findProductInfoById(Long productInfoSeq);

    void productViewSave(ProductView item);

    Optional<ProductView> productViewFindByProductInfoSeq(Long productDetailSeq);

    ProductInfo selectProductDetail(Long productInfoSeq);

    ProductDto selectProduct(Long productSeq);

    Page<ProductView> productViewFindAll(Pageable pageable);

    Page<AdminProductListDto> selectProductList(Pageable pageable);
}
