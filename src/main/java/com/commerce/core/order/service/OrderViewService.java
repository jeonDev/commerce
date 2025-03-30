package com.commerce.core.order.service;

import com.commerce.core.common.type.PageListResponse;
import com.commerce.core.order.service.request.OrderViewMergeServiceRequest;
import com.commerce.core.order.service.response.OrderViewServiceResponse;

public interface OrderViewService {

    void merge(OrderViewMergeServiceRequest request);

    PageListResponse<OrderViewServiceResponse> selectOrderView(int pageNumber, int pageSize);
}
