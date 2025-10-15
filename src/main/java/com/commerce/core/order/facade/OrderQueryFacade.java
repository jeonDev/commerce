package com.commerce.core.order.facade;

import com.commerce.core.common.type.PageListResponse;
import com.commerce.core.order.domain.OrderDao;
import com.commerce.core.order.domain.entity.mongo.OrderView;
import com.commerce.core.order.service.response.OrderViewServiceResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class OrderQueryFacade {
    private final OrderDao orderDao;

    public OrderQueryFacade(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Transactional(readOnly = true)
    public PageListResponse<OrderViewServiceResponse> selectOrderView(int pageNumber, int pageSize) {
        var pageable = PageRequest.of(pageNumber, pageSize);
        var list = orderDao.orderViewFindAll(pageable);
        return PageListResponse.<OrderViewServiceResponse>builder()
                .list(list.getContent().stream()
                        .map(OrderView::documentToResDto)
                        .toList()
                )
                .totalPage(list.getTotalPages())
                .build();
    }
}
