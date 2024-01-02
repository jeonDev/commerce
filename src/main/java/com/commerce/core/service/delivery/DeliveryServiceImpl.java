package com.commerce.core.service.delivery;

import com.commerce.core.common.exception.CommerceException;
import com.commerce.core.common.exception.ExceptionStatus;
import com.commerce.core.entity.Delivery;
import com.commerce.core.entity.OrderDetail;
import com.commerce.core.entity.repository.DeliveryRepository;
import com.commerce.core.service.order.OrderService;
import com.commerce.core.vo.delivery.DeliveryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryRepository deliveryRepository;

    private final OrderService orderService;

    @Override
    public Delivery registerDeliveryInfo(DeliveryDto dto) {
        Long orderDetailSeq = dto.getOrderDetailSeq();
        OrderDetail orderDetail = orderService.selectOrderDetail(orderDetailSeq);
        Delivery delivery = dto.dtoToEntity(orderDetail);
        return deliveryRepository.save(delivery);
    }

    @Override
    public Delivery selectDeliveryTopDetail(OrderDetail orderDetail) {
        return deliveryRepository.findTopByOrderDetailOrderByLastModifiedDtDesc(orderDetail)
                .orElseThrow(() -> new CommerceException(ExceptionStatus.ENTITY_IS_EMPTY));
    }
}
