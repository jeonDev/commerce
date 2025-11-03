package com.commerce.core.product.infra.dao;

import com.commerce.core.product.domain.entity.ProductStockHistory;
import com.commerce.core.product.domain.ProductStockHistoryDao;
import com.commerce.core.product.infra.dao.jpa.ProductStockHistoryJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ProductStockHistoryDaoImpl implements ProductStockHistoryDao {

    private final ProductStockHistoryJpaRepository productStockHistoryJpaRepository;

    public ProductStockHistoryDaoImpl(ProductStockHistoryJpaRepository productStockHistoryJpaRepository) {
        this.productStockHistoryJpaRepository = productStockHistoryJpaRepository;
    }

    @Override
    public ProductStockHistory save(ProductStockHistory productStockHistory) {
        return productStockHistoryJpaRepository.save(productStockHistory);
    }
}
