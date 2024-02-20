package com.commerce.core.product.repository.mongo;

import com.commerce.core.product.entity.mongo.ProductView;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ProductViewRepository extends MongoRepository<ProductView, Long> {
    Optional<ProductView> findByProductInfoSeq(Long productInfoSeq);
}
