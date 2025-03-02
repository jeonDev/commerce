package com.commerce.core.order.service;

import com.commerce.core.order.domain.entity.Orders;
import com.commerce.core.order.service.request.PaymentServiceRequest;

public interface PaymentService {

    Orders payment(PaymentServiceRequest dto);
}
