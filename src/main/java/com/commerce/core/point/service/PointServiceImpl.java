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
    public Point charge(PointDto dto) {
        return this.process(dto, ConsumeDivisionStatus.CHARGE);
    }

    @Transactional
    @Override
    public Point withdraw(PointDto dto) {
        return this.process(dto, ConsumeDivisionStatus.PAYMENT);
    }

    @Override
    public Optional<Point> selectPoint(Long memberSeq) {
        return pointRepository.findByMember_MemberSeq(memberSeq);
    }

    private Point process(PointDto dto, ConsumeDivisionStatus status) {
        Long memberSeq = dto.getMemberSeq();
        Member member = memberService.selectUseMember(memberSeq).orElseThrow(() -> new CommerceException(ExceptionStatus.ENTITY_IS_EMPTY));

        Optional<Point> optionalPoint = pointRepository.findByMember(member);

        if(optionalPoint.isPresent()) {
            Point point = optionalPoint.get();
            point.pointChange(dto.getPoint(), status);

            return this.entitySaveAndHistoryGenerate(point
                    , dto.getPoint()
                    , status);
        } else {
            if(status == ConsumeDivisionStatus.PAYMENT) throw new CommerceException(ExceptionStatus.POINT_LACK);

            return this.entitySaveAndHistoryGenerate(Point.builder()
                            .member(member)
                            .point(dto.getPoint())
                            .build()
                    , dto.getPoint()
                    , status);
        }
    }

    private Point entitySaveAndHistoryGenerate(Point point, Long paymentAmount, ConsumeDivisionStatus status) {
        Point result = pointRepository.save(point);
        pointHistoryRepository.save(point.generateHistoryEntity(paymentAmount, status));
        return result;
    }
}
