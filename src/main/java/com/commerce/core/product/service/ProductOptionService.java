package com.commerce.core.product.service;

import com.commerce.core.product.domain.ProductOptionDao;
import com.commerce.core.product.domain.entity.ProductOption;
import com.commerce.core.product.domain.entity.ProductStockHistory;
import com.commerce.core.product.type.ProductStockProcessStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductOptionService {
    private final ProductOptionDao productOptionDao;

    public ProductOptionService(ProductOptionDao productOptionDao) {
        this.productOptionDao = productOptionDao;
    }
    
    @Transactional
    public ProductOption merge(ProductOption productOption) {
        return productOptionDao.save(productOption);
    }

    @Transactional
    public ProductStockHistory productStockAdjustment(Long productOptionSeq, ProductStockProcessStatus productStockProcessStatus) {

        return null;
    }

}
