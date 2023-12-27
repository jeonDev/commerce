package com.commerce.core.service.impl;

import com.commerce.core.entity.Product;
import com.commerce.core.entity.dsl.ProductDslRepository;
import com.commerce.core.entity.repository.ProductRepository;
import com.commerce.core.service.ProductService;
import com.commerce.core.vo.ProductDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductDslRepository productDslRepository;

    @Override
    public ProductDto add(ProductDto dto) {
        return productRepository.save(dto.dtoToEntity())
                .entityToDto();
    }

    @Override
    public List<Product> selectProductList(ProductDto dto) {
        Page<Product> content = productDslRepository.findByAll(dto);
        return content.getContent();
    }

    @Override
    public ProductDto selectProduct(ProductDto dto) {
        return productRepository.findById(dto.getProductSeq())
                .orElseThrow()
                .entityToDto();
    }
}
