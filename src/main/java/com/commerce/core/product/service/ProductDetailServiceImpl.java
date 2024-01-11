package com.commerce.core.product.service;

import com.commerce.core.common.exception.CommerceException;
import com.commerce.core.common.exception.ExceptionStatus;
import com.commerce.core.product.entity.ProductDetail;
import com.commerce.core.product.repository.ProductDetailRepository;
import com.commerce.core.product.vo.ProductDetailDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductDetailServiceImpl implements ProductDetailService {

    private final ProductDetailRepository productDetailRepository;

    @Override
    public ProductDetail add(ProductDetailDto dto) {
        return productDetailRepository.save(dto.dtoToEntity());
    }

    @Override
    public ProductDetail selectProductDetail(Long productDetailSeq) {
        return productDetailRepository.findById(productDetailSeq)
                .orElseThrow(() -> new CommerceException(ExceptionStatus.ENTITY_IS_EMPTY));
    }
}
