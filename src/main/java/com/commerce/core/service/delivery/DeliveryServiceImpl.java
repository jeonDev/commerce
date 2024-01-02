package com.commerce.core.service.delivery;

import com.commerce.core.entity.Delivery;
import com.commerce.core.entity.repository.DeliveryRepository;
import com.commerce.core.vo.delivery.DeliveryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryRepository deliveryRepository;

    @Override
    public Delivery registerDeliveryInfo(DeliveryDto dto) {

        return null;
    }
}
