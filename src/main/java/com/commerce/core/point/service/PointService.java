package com.commerce.core.point.service;

import com.commerce.core.common.vo.PageListVO;
import com.commerce.core.point.domain.entity.MemberPoint;
import com.commerce.core.point.vo.PointDto;

import java.util.Optional;

public interface PointService {

    /**
     * Point Adjustment
     */
    PointDto pointAdjustment(PointDto dto);

    /**
     * Point History
     */
    PageListVO<PointDto> selectPointHistory(int pageNumber, int pageSize, Long memberSeq);

    /**
     * Point Select
     */
    Optional<MemberPoint> selectPoint(Long memberSeq);
}
