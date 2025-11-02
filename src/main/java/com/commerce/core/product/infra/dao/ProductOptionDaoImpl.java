package com.commerce.core.product.infra.dao;

import com.commerce.core.product.domain.ProductOptionDao;
import com.commerce.core.product.domain.entity.ProductOption;
import com.commerce.core.product.infra.dao.jpa.ProductOptionJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ProductOptionDaoImpl implements ProductOptionDao {

    private final ProductOptionJpaRepository productOptionJpaRepository;

    public ProductOptionDaoImpl(ProductOptionJpaRepository productOptionJpaRepository) {
        this.productOptionJpaRepository = productOptionJpaRepository;
    }

    @Override
    public ProductOption save(ProductOption productOption) {
        return productOptionJpaRepository.save(productOption);
    }

    @Override
    public Optional<ProductOption> findById(Long productOptionSeq) {
        return productOptionJpaRepository.findById(productOptionSeq);
    }
}
