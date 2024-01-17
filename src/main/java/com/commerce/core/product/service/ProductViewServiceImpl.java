package com.commerce.core.product.service;

import com.commerce.core.product.entity.mongo.ProductView;
import com.commerce.core.product.repository.mongo.ProductViewRepository;
import com.commerce.core.product.vo.ProductViewDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductViewServiceImpl implements ProductViewService {

    private final ProductViewRepository productViewRepository;

    @Override
    public ProductView register(ProductViewDto dto) {
        return productViewRepository.save(dto.dtoToEntity());
    }

    @Override
    public ProductView update(ProductViewDto dto) {
        return null;
    }

    @Override
    public Optional<ProductView> selectProductViewForProductDetail(Long productDetailSeq) {
        return productViewRepository.findByProductInfoSeq(productDetailSeq);
    }

    @Override
    public Optional<ProductView> selectProductView(Long productViewSeq) {
        return Optional.empty();
    }
}
