package com.commerce.core.product.service;

import com.commerce.core.product.entity.ProductInfo;
import com.commerce.core.product.repository.ProductInfoRepository;
import com.commerce.core.product.vo.ProductInfoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductInfoServiceImpl implements ProductInfoService {

    private final ProductInfoRepository productInfoRepository;

    @Override
    public ProductInfo add(ProductInfoDto dto) {
        return productInfoRepository.save(dto.dtoToEntity());
    }

    @Override
    public Optional<ProductInfo> selectProductInfo(Long productInfoSeq) {
        return productInfoRepository.findById(productInfoSeq);
    }
}
