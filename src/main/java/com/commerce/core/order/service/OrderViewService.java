package com.commerce.core.order.service;

import com.commerce.core.common.vo.PageListVO;
import com.commerce.core.order.service.request.OrderViewMergeServiceRequest;
import com.commerce.core.order.vo.OrderViewResDto;

public interface OrderViewService {

    void merge(OrderViewMergeServiceRequest request);

    PageListVO<OrderViewResDto> selectOrderView(int pageNumber, int pageSize);
}
