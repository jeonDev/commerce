package com.commerce.core.service.product;

import com.commerce.core.common.exception.CommerceException;
import com.commerce.core.common.exception.ExceptionStatus;
import com.commerce.core.entity.Product;
import com.commerce.core.entity.repository.dsl.ProductDslRepository;
import com.commerce.core.entity.repository.ProductRepository;
import com.commerce.core.vo.product.ProductDto;
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

    /**
     * Product Add
     * @param dto
     * @return
     */
    @Override
    public Product add(ProductDto dto) {
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
     * @param dto
     * @return
     */
    @Override
    public Product selectProduct(ProductDto dto) {
        return productRepository.findById(dto.getProductSeq())
                .orElseThrow(() -> new CommerceException(ExceptionStatus.ENTITY_IS_EMPTY));
    }
}
