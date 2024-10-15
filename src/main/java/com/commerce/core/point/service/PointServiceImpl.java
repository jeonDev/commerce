package com.commerce.core.point.service;

import com.commerce.core.common.exception.CommerceException;
import com.commerce.core.common.exception.ExceptionStatus;
import com.commerce.core.common.vo.PageListVO;
import com.commerce.core.member.entity.Member;
import com.commerce.core.member.service.MemberService;
import com.commerce.core.point.entity.MemberPoint;
import com.commerce.core.point.entity.PointHistory;
import com.commerce.core.point.repository.PointHistoryRepository;
import com.commerce.core.point.repository.PointRepository;
import com.commerce.core.point.vo.PointDto;
import com.commerce.core.point.vo.PointProcessStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    public PointDto pointAdjustment(PointDto dto) {
        // 1. Member Use Find
        Member member = memberService.selectUseMember(dto.getMemberSeq())
                .orElseThrow(() -> new CommerceException(ExceptionStatus.ENTITY_IS_EMPTY));

        // 2. Existing Point Find & Point Setting
        Optional<MemberPoint> optionalPoint = pointRepository.findByMember(member);
        MemberPoint point;
        if(optionalPoint.isPresent()) {
            point = optionalPoint.get();
            point.pointChange(dto.getPoint(), dto.getPointProcessStatus());
        } else {
            if(dto.getPointProcessStatus() == PointProcessStatus.PAYMENT) throw new CommerceException(ExceptionStatus.POINT_LACK);

            point = MemberPoint.builder()
                    .member(member)
                    .point(dto.getPoint())
                    .build();
        }

        // 3. Entity And History Save
        this.entitySaveAndHistoryGenerate(point, dto);

        return PointDto.builder()
                .memberSeq(member.getMemberSeq())
                .point(dto.getPoint())
                .balancePoint(point.getPoint())
                .build();
    }

    private void entitySaveAndHistoryGenerate(MemberPoint point, PointDto dto) {
        pointRepository.save(point);
        pointHistoryRepository.save(point.generateHistoryEntity(dto.getPoint(), dto.getPointProcessStatus()));
    }

    @Transactional(readOnly = true)
    @Override
    public PageListVO<PointDto> selectPointHistory(int pageNumber, int pageSize, Long memberSeq) {
        Member member = memberService.selectMember(memberSeq)
                .orElseThrow(() -> new CommerceException(ExceptionStatus.ENTITY_IS_EMPTY));

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<PointHistory> list = pointHistoryRepository.findByMember(pageable, member);

        return PageListVO.<PointDto>builder()
                .list(list.getContent().stream()
                        .map(PointHistory::entityToResponse)
                        .toList()
                )
                .totalPage(list.getTotalPages())
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<MemberPoint> selectPoint(Long memberSeq) {
        return pointRepository.findByMember_MemberSeq(memberSeq);
    }

}
