package com.commerce.core.product.facade;

import com.commerce.core.product.domain.BrandDao;
import com.commerce.core.product.domain.CategoryDao;
import com.commerce.core.product.domain.entity.Brand;
import com.commerce.core.product.domain.entity.Category;
import com.commerce.core.product.domain.entity.Product;
import com.commerce.core.product.domain.entity.ProductOption;
import com.commerce.core.product.facade.request.ProductAddRequest;
import com.commerce.core.product.service.ProductOptionService;
import com.commerce.core.product.service.ProductService;
import com.commerce.core.product.facade.response.ProductServiceResponse;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class ProductFacade {

    private final ProductService productService;
    private final ProductOptionService productOptionService;
    private final BrandDao brandDao;
    private final CategoryDao categoryDao;

    public ProductFacade(ProductService productService,
                         ProductOptionService productOptionService,
                         BrandDao brandDao,
                         CategoryDao categoryDao
    ) {
        this.productService = productService;
        this.productOptionService = productOptionService;
        this.brandDao = brandDao;
        this.categoryDao = categoryDao;
    }

    @Transactional
    public ProductServiceResponse add(ProductAddRequest request) {
        // 1. 브랜드 유무 조회
        Brand brand = brandDao.findById(request.brandSeq())
                .orElseThrow();

        // 2. 카테고리 유무 조회
        Category category = categoryDao.findById(request.categorySeq())
                .orElseThrow();

        // 3. 상품 등록
        Product product = productService.add(request.toEntity(brand, category));

        // 4. 상품 옵션 등록
        List<ProductOption> productOptionList = request.productOptionRequestList().stream()
                .map(item -> productOptionService.merge(item.toEntity(product)))
                .toList();

        return ProductServiceResponse.builder()
                .build();
    }
}
