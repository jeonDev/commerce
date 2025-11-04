package com.commerce.core.product.infra.dao.jpa;

import com.commerce.core.product.domain.entity.Product;
import com.commerce.core.product.domain.entity.ProductOption;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductOptionJpaRepository extends JpaRepository<ProductOption, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({ @QueryHint(name = "javax.persistence.lock.timeout", value = "5000") }) // 5초
    @Query("SELECT p FROM ProductOption p WHERE p.productOptionSeq = :productOptionSeq")
    Optional<ProductOption> findByIdForUpdate(@Param("productOptionSeq") Long productOptionSeq);

    List<ProductOption> findByProduct(Product product);
}
