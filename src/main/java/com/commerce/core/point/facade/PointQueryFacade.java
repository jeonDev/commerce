package com.commerce.core.point.facade;

import com.commerce.core.common.type.PageListResponse;
import com.commerce.core.point.domain.PointDao;
import com.commerce.core.point.service.response.PointAdjustmentServiceResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class PointQueryFacade {

    private final PointDao pointDao;

    public PointQueryFacade(PointDao pointDao) {
        this.pointDao = pointDao;
    }

    @Transactional(readOnly = true)
    public PageListResponse<PointAdjustmentServiceResponse> selectPointHistory(Integer pageNumber, Integer pageSize, Long memberSeq) {
        var pageable = PageRequest.of(pageNumber, pageSize);
        var list = pointDao.findByMemberPaging(pageable, memberSeq);

        return PageListResponse.<PointAdjustmentServiceResponse>builder()
                .list(list.getContent().stream()
                        .map(PointAdjustmentServiceResponse::from)
                        .toList()
                )
                .totalPage(list.getTotalPages())
                .build();
    }
}
