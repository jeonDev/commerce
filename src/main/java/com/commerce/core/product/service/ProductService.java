package com.commerce.core.product.service;

import com.commerce.core.product.domain.ProductDao;
import com.commerce.core.product.domain.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class ProductService {

    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Transactional
    public Product add(Product product) {
        return productDao.save(product);
    }
}
