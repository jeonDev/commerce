package com.commerce.core.point.service;

import com.commerce.core.common.type.PageListResponse;
import com.commerce.core.point.domain.entity.MemberPoint;
import com.commerce.core.point.service.request.PointAdjustmentServiceRequest;
import com.commerce.core.point.service.response.PointAdjustmentServiceResponse;

import java.util.Optional;

public interface PointService {

    /**
     * Point Adjustment
     */
    PointAdjustmentServiceResponse pointAdjustment(PointAdjustmentServiceRequest dto);

    /**
     * Point History
     */
    PageListResponse<PointAdjustmentServiceResponse> selectPointHistory(int pageNumber, int pageSize, Long memberSeq);

    /**
     * Point Select
     */
    Optional<MemberPoint> selectPoint(Long memberSeq);
}
