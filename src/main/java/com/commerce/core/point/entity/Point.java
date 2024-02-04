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
    private Long memberSeq;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_SEQ")
    private Member member;

    @ColumnDefault("0")
    @Column(name = "POINT", nullable = false)
    private BigDecimal point;

    public void pointChange(BigDecimal point, ConsumeDivisionStatus status) {
        if(status == ConsumeDivisionStatus.CHARGE) {
            this.point = this.point.add(point);
        } else if (status == ConsumeDivisionStatus.PAYMENT) {
            this.point = this.point.subtract(point);
        }

        if(this.point.compareTo(BigDecimal.ZERO) <= 0) {
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
