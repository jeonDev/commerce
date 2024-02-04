package com.commerce.core.point.entity;

import com.commerce.core.common.entity.BaseEntity;
import com.commerce.core.common.exception.CommerceException;
import com.commerce.core.common.exception.ExceptionStatus;
import com.commerce.core.member.entity.Member;
import com.commerce.core.point.vo.ConsumeDivisionStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Table(name = "POINT")
public class Point extends BaseEntity {

    @Id
    @Column(name = "MEMBER_SEQ")
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_SEQ")
    private Member member;

    @ColumnDefault("0")
    @Column(name = "POINT", nullable = false)
    private Long point;

    public void pointChange(Long point, ConsumeDivisionStatus status) {
        if(status == ConsumeDivisionStatus.CHARGE) {
            this.point += point;
        } else if (status == ConsumeDivisionStatus.PAYMENT) {
            this.point -= point;
        }

        if(this.point < 0) {
            throw new CommerceException(ExceptionStatus.POINT_LACK);
        }
    }

    public PointHistory generateHistoryEntity(ConsumeDivisionStatus status) {
        return PointHistory.builder()
                .member(this.member)
                .point(this.point)
                .consumeDivisionStatus(status)
                .build();
    }

}
