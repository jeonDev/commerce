package com.commerce.core.product.service;

import com.commerce.core.product.domain.BrandDao;
import com.commerce.core.product.domain.entity.Brand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BrandService {
    private final BrandDao brandDao;

    public BrandService(BrandDao brandDao) {
        this.brandDao = brandDao;
    }

    @Transactional
    public Brand merge(Brand brand) {
        return brandDao.save(brand);
    }
}
