package com.commerce.core.product.service;

import com.commerce.core.product.entity.mongo.ProductView;
import com.commerce.core.product.repository.mongo.ProductViewRepository;
import com.commerce.core.product.vo.ProductViewDto;
import com.commerce.core.product.vo.ProductViewResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductViewServiceImpl implements ProductViewService {

    private final ProductViewRepository productViewRepository;

    @Override
    public void merge(ProductViewDto dto) {
        // 1. 기존 데이터 존재여부 체크
        Long productInfoSeq = dto.getProductInfoSeq();
        Optional<ProductView> optionalProductView = this.selectProductViewForProductDetail(productInfoSeq);
        optionalProductView.ifPresentOrElse(item -> {
            productViewRepository.save(item.syncProductView(dto));
        }, () -> {
            productViewRepository.save(dto.dtoToEntity());
        });
    }

    @Override
    public Optional<ProductView> selectProductViewForProductDetail(Long productDetailSeq) {
        return productViewRepository.findByProductInfoSeq(productDetailSeq);
    }

    @Override
    public Optional<ProductView> selectProductViewDetail(Long productViewSeq) {
        return Optional.empty();
    }

    @Override
    public List<ProductViewResDto> selectProductViewList() {
        return productViewRepository.findAll().stream()
                .map(ProductView::documentToResDto)
                .toList();
    }
}
