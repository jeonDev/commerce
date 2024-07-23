package com.commerce.core.order.entity.mongo;

import com.commerce.core.order.vo.OrderDetailInfo;
import com.commerce.core.order.vo.OrderViewResDto;
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

    @Id
    private String id;

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

    public OrderViewResDto documentToResDto() {
        return OrderViewResDto.builder()
                .orderSeq(orderSeq)
                .orderDetailInfos(orderDetailInfos)
                .amount(amount)
                .buyAmount(buyAmount)
                .paidAmount(paidAmount)
                .build();
    }
}
