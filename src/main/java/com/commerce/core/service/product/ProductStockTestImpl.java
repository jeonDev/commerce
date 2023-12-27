package com.commerce.core.service.product;

import com.commerce.core.entity.ProductStock;
import com.commerce.core.vo.product.ProductStockDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
@Profile("test")
public class ProductStockTestImpl implements ProductStockService {

    @Override
    public ProductStock register(ProductStockDto dto) {
        return null;
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
