package com.commerce.core.order.entity.mongo;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "ORDER_VIEW")
public class OrderView {

    /**
     * 주문 정보 View Seq
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderViewSeq;
}
