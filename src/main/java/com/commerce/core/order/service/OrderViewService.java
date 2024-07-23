package com.commerce.core.order.service;

import com.commerce.core.common.vo.PageListVO;
import com.commerce.core.order.vo.OrderViewDto;
import com.commerce.core.order.vo.OrderViewResDto;

public interface OrderViewService {

    void merge(OrderViewDto dto);

    PageListVO<OrderViewResDto> selectOrderView(int pageNumber, int pageSize);
}
