package com.commerce.core.service.product;

import com.commerce.core.common.exception.CommerceException;
import com.commerce.core.common.exception.ExceptionStatus;
import com.commerce.core.entity.Product;
import com.commerce.core.entity.ProductStock;
import com.commerce.core.entity.repository.ProductStockHistoryRepository;
import com.commerce.core.entity.repository.ProductStockRepository;
import com.commerce.core.vo.product.ProductDto;
import com.commerce.core.vo.product.ProductStockDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
@Profile("test")
public class ProductStockTestImpl implements ProductStockService {

    private final ProductStockRepository productStockRepository;
    private final ProductStockHistoryRepository productStockHistoryRepository;
    private final ProductService productService;

    @Override
    @Transactional
    public ProductStock adjustment(ProductStockDto dto) {
        // 1. 상품 존재 여부 체크
        Product product = this.getProductDetail(dto);

        // 2. 재고 조정 (기존 데이터 존재 여부 체크)
        ProductStock entity = null;
        Optional<ProductStock> optionalProductStock = productStockRepository.findWithPessimisticLockById(product.getProductSeq());
        if(optionalProductStock.isPresent()) {
            entity = optionalProductStock.get();
            entity.inventoryAdjustment(dto.getStock());
        } else {
            entity = dto.dtoToEntity(product);
        }

        if(STOCK_SOLD_OUT_COUNT >= entity.getStock())
            throw new CommerceException(ExceptionStatus.SOLD_OUT);

        entity = productStockRepository.save(entity);

        // 3. 재고 처리 내역 저장
        this.saveHistoryEntity(entity);
        return entity;
    }

    @Override
    public ProductStock selectProductStock(ProductStockDto dto) {
        return productStockRepository.findById(dto.getProductSeq())
                .orElseThrow(() -> new CommerceException(ExceptionStatus.ENTITY_IS_EMPTY));
    }

    /**
     * 상품 존재 여부 체크
     * @param dto
     * @return
     */
    private Product getProductDetail(ProductStockDto dto) {
        ProductDto productDto = ProductDto.builder().productSeq(dto.getProductSeq()).build();
        return productService.selectProduct(productDto);
    }

    /**
     * 재고 처리 내역 저장
     * @param entity
     */
    private void saveHistoryEntity(ProductStock entity) {
        productStockHistoryRepository.save(entity.generateHistoryEntity());
    }
}
