package com.commerce.core.product.domain;

import com.commerce.core.product.domain.entity.ProductStock;
import com.commerce.core.product.domain.entity.ProductStockHistory;
import com.commerce.core.product.domain.repository.ProductStockHistoryRepository;
import com.commerce.core.product.domain.repository.ProductStockRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ProductStockDaoImpl implements ProductStockDao {

    private final ProductStockRepository productStockRepository;
    private final ProductStockHistoryRepository productStockHistoryRepository;

    public ProductStockDaoImpl(ProductStockRepository productStockRepository,
                               ProductStockHistoryRepository productStockHistoryRepository) {
        this.productStockRepository = productStockRepository;
        this.productStockHistoryRepository = productStockHistoryRepository;
    }

    @Override
    public void save(ProductStock productStock) {
        productStockRepository.save(productStock);
    }

    @Override
    public void productStockHistorySave(ProductStockHistory productStockHistory) {
        productStockHistoryRepository.save(productStockHistory);
    }

    @Override
    public Optional<ProductStock> lockFindById(Long productSeq) {
        return productStockRepository.findWithPessimisticLockById(productSeq);
    }

    @Override
    public Optional<ProductStock> productStockFindById(Long productSeq) {
        return productStockRepository.findById(productSeq);
    }
}
