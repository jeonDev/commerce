package com.commerce.core.service.product;

import com.commerce.core.config.redis.RedissonLockTarget;
import com.commerce.core.config.redis.RedisKeyType;
import com.commerce.core.entity.Product;
import com.commerce.core.entity.ProductStock;
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
@Profile("basic")
public class ProductStockServiceImpl implements ProductStockService {

    private final ProductStockRepository productStockRepository;
    private final ProductService productService;

    @RedissonLockTarget(value = RedisKeyType.PRODUCT_STOCK, delay = 100)
    @Transactional
    @Override
    public ProductStock register(ProductStockDto dto) {
        // 1. 상품 존재 여부 체크
        ProductDto productDto = ProductDto.builder().productSeq(dto.getProductSeq()).build();
        Product product = productService.selectProduct(productDto);
        if(product == null) throw new IllegalArgumentException();

        // 2. 재고 조정 (기존 데이터 존재여부 체크)
        ProductStock entity = null;
        Optional<ProductStock> optionalProductStock = productStockRepository.findById(dto.getProductSeq());
        if(optionalProductStock.isPresent()) {
            entity = optionalProductStock.get();
            entity.inventoryAdjustment(dto.getStock());
        } else {
            entity = dto.dtoToEntity();
        }
        return productStockRepository.save(entity);
    }

    @Override
    public ProductStock released(ProductStockDto dto) {
        return null;
    }

    @Override
    public ProductStock selectProductStock(ProductStockDto dto) {
        return null;
    }
}
