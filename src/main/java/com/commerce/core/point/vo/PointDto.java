package com.commerce.core.point.vo;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class PointDto {
    @Setter
    private Long memberSeq;

    @Min(value = 0, message = "금액을 확인해주세요.")
    @NotNull(message = "금액을 확인해주세요.")
    private Long point;

    private Long balancePoint;

    @NotNull
    private PointProcessStatus pointProcessStatus;

    private LocalDateTime createDt;
}
