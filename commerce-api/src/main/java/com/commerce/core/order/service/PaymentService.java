package com.commerce.core.order.service;

import com.commerce.core.order.entity.Orders;
import com.commerce.core.order.vo.PaymentDto;

public interface PaymentService {

    Orders payment(PaymentDto dto);
}
