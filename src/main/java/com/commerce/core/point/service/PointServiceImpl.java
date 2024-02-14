package com.commerce.core.point.service;

import com.commerce.core.common.exception.CommerceException;
import com.commerce.core.common.exception.ExceptionStatus;
import com.commerce.core.member.entity.Member;
import com.commerce.core.member.service.MemberService;
import com.commerce.core.point.entity.Point;
import com.commerce.core.point.repository.PointHistoryRepository;
import com.commerce.core.point.repository.PointRepository;
import com.commerce.core.point.vo.ConsumeDivisionStatus;
import com.commerce.core.point.vo.PointDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class PointServiceImpl implements PointService {

    private final PointRepository pointRepository;
    private final PointHistoryRepository pointHistoryRepository;

    private final MemberService memberService;

    @Transactional
    @Override
    public PointDto charge(PointDto dto) {
        return this.process(dto, ConsumeDivisionStatus.CHARGE);
    }

    @Transactional
    @Override
    public PointDto withdraw(PointDto dto) {
        return this.process(dto, ConsumeDivisionStatus.PAYMENT);
    }

    @Override
    public Optional<Point> selectPoint(Long memberSeq) {
        return pointRepository.findByMember_MemberSeq(memberSeq);
    }

    private PointDto process(PointDto dto, ConsumeDivisionStatus status) {
        // 1. Member Use Find
        Member member = memberService.selectUseMember(dto.getMemberSeq())
                .orElseThrow(() -> new CommerceException(ExceptionStatus.ENTITY_IS_EMPTY));

        // 2. Existing Point Find & Point Setting
        Optional<Point> optionalPoint = pointRepository.findByMember(member);
        Point point;
        if(optionalPoint.isPresent()) {
            point = optionalPoint.get();
            point.pointChange(dto.getPoint(), status);
        } else {
            if(status == ConsumeDivisionStatus.PAYMENT) throw new CommerceException(ExceptionStatus.POINT_LACK);

            point = Point.builder()
                    .member(member)
                    .point(dto.getPoint())
                    .build();
        }

        // 3. Entity And History Save
        this.entitySaveAndHistoryGenerate(point, dto.getPoint(), status);

        return PointDto.builder()
                .memberSeq(member.getMemberSeq())
                .point(dto.getPoint())
                .balancePoint(point.getPoint())
                .build();
    }

    private void entitySaveAndHistoryGenerate(Point point, Long paymentAmount, ConsumeDivisionStatus status) {
        pointRepository.save(point);
        pointHistoryRepository.save(point.generateHistoryEntity(paymentAmount, status));
    }
}
