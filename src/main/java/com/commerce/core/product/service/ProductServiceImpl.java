package com.commerce.core.product.service;

import com.commerce.core.product.entity.Product;
import com.commerce.core.product.repository.dsl.ProductDslRepository;
import com.commerce.core.product.repository.ProductRepository;
import com.commerce.core.product.vo.ProductDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductDslRepository productDslRepository;

    /**
     * Product Add
     * @param dto
     * @return
     */
    @Override
    public Product add(ProductDto dto) {
        // TODO: 상품 부가 엔티티 조회 후 저장 - 테스트 코드 추가
        return productRepository.save(dto.dtoToEntity());
    }

    /**
     * Select Product List
     * @param dto
     * @return
     */
    @Override
    public List<Product> selectProductList(ProductDto dto) {
        Page<Product> content = productDslRepository.findByAll(dto);
        return content.getContent();
    }

    /**
     * Select Product Detail
     * @param productSeq
     * @return
     */
    @Override
    public Optional<Product> selectProduct(Long productSeq) {
        return productRepository.findById(productSeq);
    }

    @Override
    public List<?> selectSalesProducts() {
        return null;
    }
}
