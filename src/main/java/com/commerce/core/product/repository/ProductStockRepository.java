package com.commerce.core.product.repository;

import com.commerce.core.product.entity.Product;
import com.commerce.core.product.entity.ProductStock;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;

public interface ProductStockRepository extends JpaRepository<ProductStock, Product> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<ProductStock> findWithPessimisticLockById(Long productSeq);

    Optional<ProductStock> findById(Long productSeq);
}
