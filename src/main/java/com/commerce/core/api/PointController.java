package com.commerce.core.api;

import com.commerce.core.point.service.PointService;
import com.commerce.core.point.vo.PointDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/v1/point")
@RestController
public class PointController {

    private final PointService pointService;

    @PostMapping("/charege")
    public void pointCharge(@RequestBody PointDto pointDto) {
        pointService.charge(pointDto);
    }
}
