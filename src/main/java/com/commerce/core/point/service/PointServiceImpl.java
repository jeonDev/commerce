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

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class PointServiceImpl implements PointService {

    private final PointRepository pointRepository;
    private final PointHistoryRepository pointHistoryRepository;

    private final MemberService memberService;

    @Override
    public Point charge(PointDto dto) {
        return this.process(dto, ConsumeDivisionStatus.CHARGE);
    }

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

        Optional<Point> optionalPoint = pointRepository.findById(member.getMemberSeq());
        Point point;
        if(optionalPoint.isPresent()) {
            point = optionalPoint.get();

            point.pointChange(dto.getPoint(), status);
            point = this.pointSave(point, status);
        } else {
            if(status == ConsumeDivisionStatus.PAYMENT) throw new CommerceException(ExceptionStatus.POINT_LACK);
            point = this.pointSave(Point.builder()
                            .member(member)
                            .point(dto.getPoint())
                            .build()
                    , status);
        }
        return point;
    }

    private Point pointSave(Point point, ConsumeDivisionStatus status) {
        Point result = pointRepository.save(point);
        pointHistoryRepository.save(point.generateHistoryEntity(status));
        return result;
    }
}
