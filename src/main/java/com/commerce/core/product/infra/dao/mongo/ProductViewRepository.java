package com.commerce.core.product.infra.dao.mongo;

import com.commerce.core.product.domain.entity.ProductView;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ProductViewRepository extends MongoRepository<ProductView, Long> {
    Optional<ProductView> findByProductInfoSeq(Long productInfoSeq);
}
