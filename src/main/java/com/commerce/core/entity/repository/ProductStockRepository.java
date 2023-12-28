package com.commerce.core.entity.repository;

import com.commerce.core.entity.ProductStock;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;

public interface ProductStockRepository extends JpaRepository<ProductStock, Long> {

    /** H2 테스트 용 select for update */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<ProductStock> findWithPessimisticLockByProductSeq(Long productSeq);
}
