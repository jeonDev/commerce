package com.commerce.core.point.domain.entity;

import com.commerce.core.common.entity.BaseEntity;
import com.commerce.core.point.type.PointProcessStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "POINT_HISTORY")
public class PointHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POINT_HISTORY_SEQ")
    private Long pointHistorySeq;

    @Column(name = "MEMBER_SEQ")
    private Long memberSeq;

    @Column(name = "POINT")
    private Long point;

    @Column(name = "POINT_PROCESS_STATUS")
    @Enumerated(EnumType.ORDINAL)
    private PointProcessStatus pointProcessStatus;

    public static PointHistory of(
            Long memberSeq,
            Long point,
            PointProcessStatus pointProcessStatus
    ) {
        return new PointHistory(
                null,
                memberSeq,
                point,
                pointProcessStatus
        );
    }
}
