package com.commerce.core.api;

import com.commerce.core.api.request.PointRequest;
import com.commerce.core.api.response.PointAdjustmentResponse;
import com.commerce.core.common.utils.SessionUtils;
import com.commerce.core.common.vo.PageListVO;
import com.commerce.core.common.vo.ResponseVO;
import com.commerce.core.point.service.PointService;
import com.commerce.core.point.service.response.PointAdjustmentServiceResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Tag(name = "사용자 포인트 API")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/v1/point")
@RestController
public class PointController {

    private final PointService pointService;

    @PostMapping("/adjustment")
    @Operation(summary = "포인트 충전/차감", description = "고객의 포인트를 충전 및 차감한다")
    public ResponseVO<PointAdjustmentResponse> pointCharge(@Valid @RequestBody PointRequest request) {

        return ResponseVO.<PointAdjustmentResponse>builder()
                .data(PointAdjustmentResponse.from(
                        pointService.pointAdjustment(request.toRequest()))
                )
                .build();
    }

    @GetMapping("/history")
    @Operation(summary = "포인트 내역", description = "포인트 충전/차감 내역 조회")
    public ResponseVO<PageListVO<PointAdjustmentServiceResponse>> history(@RequestParam(name = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                                                          @RequestParam(name = "pageSize", defaultValue = "10", required = false) Integer pageSize) {
        PageListVO<PointAdjustmentServiceResponse> result = pointService.selectPointHistory(pageNumber, pageSize, SessionUtils.getMemberSeq());
        return ResponseVO.<PageListVO<PointAdjustmentServiceResponse>>builder()
                .data(result)
                .build();
    }
}
