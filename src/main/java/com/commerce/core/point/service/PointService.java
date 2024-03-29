package com.commerce.core.point.service;

import com.commerce.core.point.entity.Point;
import com.commerce.core.point.vo.PointDto;

import java.util.Optional;

public interface PointService {

    /**
     * Point Adjustment
     */
    PointDto pointAdjustment(PointDto dto);

    /**
     * Point Select
     */
    Optional<Point> selectPoint(Long memberSeq);
}
