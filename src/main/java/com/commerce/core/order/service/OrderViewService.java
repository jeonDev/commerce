package com.commerce.core.order.service;

import com.commerce.core.common.vo.PageListVO;
import com.commerce.core.order.service.request.OrderViewMergeServiceRequest;
import com.commerce.core.order.service.response.OrderViewServiceResponse;

public interface OrderViewService {

    void merge(OrderViewMergeServiceRequest request);

    PageListVO<OrderViewServiceResponse> selectOrderView(int pageNumber, int pageSize);
}
