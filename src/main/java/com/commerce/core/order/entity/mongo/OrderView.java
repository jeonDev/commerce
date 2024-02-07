package com.commerce.core.order.entity.mongo;

import com.commerce.core.order.vo.OrderDetailInfo;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

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

    private Long orderSeq;

    private List<OrderDetailInfo> orderDetailInfos;

    private Long amount;
    private Long buyAmount;
    private Long paidAmount;

    public void settingData(Long amount, Long buyAmount, Long paidAmount, List<OrderDetailInfo> orderDetailInfos) {
        this.amount = amount;
        this.buyAmount = buyAmount;
        this.paidAmount = paidAmount;
        this.orderDetailInfos = orderDetailInfos;
    }
}
