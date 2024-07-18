package com.commerce.core.api;

import com.commerce.core.common.utils.SessionUtils;
import com.commerce.core.common.vo.ResponseVO;
import com.commerce.core.point.service.PointService;
import com.commerce.core.point.vo.PointDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "사용자 포인트 API")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/v1/point")
@RestController
public class PointController {

    private final PointService pointService;

    @PostMapping("/adjustment")
    @Operation(summary = "포인트 충전/차감", description = "고객의 포인트를 충전 및 차감한다")
    public ResponseVO<PointDto> pointCharge(@RequestBody PointDto pointDto) {
        UserDetails myUserInfo = SessionUtils.getMyUserInfo();
        assert myUserInfo != null;
        String username = myUserInfo.getUsername();
        pointDto.setMemberSeq(Long.parseLong(username));
        PointDto data = pointService.pointAdjustment(pointDto);
        return ResponseVO.<PointDto>builder()
                .data(data)
                .build();
    }
}
