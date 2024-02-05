package com.commerce.core.point.entity;

import com.commerce.core.common.entity.BaseEntity;
import com.commerce.core.member.entity.Member;
import com.commerce.core.point.vo.ConsumeDivisionStatus;
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

    @Column(name = "CONSUME_DIVISION_STATUS")
    @Enumerated(EnumType.ORDINAL)
    private ConsumeDivisionStatus consumeDivisionStatus;


}
