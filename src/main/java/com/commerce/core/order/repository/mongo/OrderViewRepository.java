package com.commerce.core.order.repository.mongo;

import com.commerce.core.order.entity.mongo.OrderView;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface OrderViewRepository extends MongoRepository<OrderView, Long> {
    Optional<OrderView> findByOrderSeq(Long orderSeq);
}
