package com.commerce.core.product.infra.dao;

import com.commerce.core.product.domain.ProductDao;
import com.commerce.core.product.domain.entity.Product;
import com.commerce.core.product.infra.dao.jpa.ProductJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ProductDaoImpl implements ProductDao {

    private final ProductJpaRepository productRepository;

    public ProductDaoImpl(ProductJpaRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Optional<Product> findById(Long productSeq) {
        return productRepository.findById(productSeq);
    }

}
