package com.commerce.core.point.domain.entity;

import com.commerce.core.common.entity.BaseEntity;
import com.commerce.core.member.domain.entity.Member;
import com.commerce.core.point.service.request.PointAdjustmentServiceRequest;
import com.commerce.core.point.service.response.PointAdjustmentServiceResponse;
import com.commerce.core.point.vo.PointProcessStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Table(name = "POINT_HISTORY")
public class PointHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POINT_HISTORY_SEQ")
    private Long pointHistorySeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_SEQ")
    private Member member;

    @Column(name = "POINT")
    private Long point;

    @Column(name = "POINT_PROCESS_STATUS")
    @Enumerated(EnumType.ORDINAL)
    private PointProcessStatus pointProcessStatus;

    public PointAdjustmentServiceResponse entityToResponse() {
        return PointAdjustmentServiceResponse.builder()
                .memberSeq(member.getMemberSeq())
                .point(point)
                .pointProcessStatus(pointProcessStatus)
                .createDt(this.getCreateDt())
                .build();
    }
}
