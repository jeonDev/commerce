package com.commerce.core.product.infra.dao;

import com.commerce.core.product.domain.BrandDao;
import com.commerce.core.product.domain.entity.Brand;
import com.commerce.core.product.infra.dao.jpa.BrandJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class BrandDaoImpl implements BrandDao {
    private final BrandJpaRepository brandJpaRepository;

    public BrandDaoImpl(BrandJpaRepository brandJpaRepository) {
        this.brandJpaRepository = brandJpaRepository;
    }

    @Override
    public Brand save(Brand brand) {
        return brandJpaRepository.save(brand);
    }

    @Override
    public Optional<Brand> findById(Long brandSeq) {
        return brandJpaRepository.findById(brandSeq);
    }
}
