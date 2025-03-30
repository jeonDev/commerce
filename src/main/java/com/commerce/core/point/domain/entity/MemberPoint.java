package com.commerce.core.point.domain.entity;

import com.commerce.core.common.entity.BaseEntity;
import com.commerce.core.common.exception.CommerceException;
import com.commerce.core.common.exception.ExceptionStatus;
import com.commerce.core.member.domain.entity.Member;
import com.commerce.core.point.type.PointProcessStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Table(name = "MEMBER_POINT")
public class MemberPoint extends BaseEntity {

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

    public void pointAdjustment(Long point, PointProcessStatus status) {
        if (point <= 0 || status == null) throw new CommerceException(ExceptionStatus.VALID_ERROR);

        if (status == PointProcessStatus.CHARGE) {
            this.depositPoint(point);
        } else if (status == PointProcessStatus.PAYMENT) {
            this.withdrawPoint(point);
        }
    }

    private void withdrawPoint(Long point) {
        if(this.point - point < 0) {
            throw new CommerceException(ExceptionStatus.POINT_LACK);
        }
        this.point -= point;
    }

    private void depositPoint(Long point) {
        this.point += point;
    }

    public PointHistory generateHistoryEntity(Long point, PointProcessStatus status) {
        return PointHistory.builder()
                .member(this.member)
                .point(point)
                .pointProcessStatus(status)
                .build();
    }

}
