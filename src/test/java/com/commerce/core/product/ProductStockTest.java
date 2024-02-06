package com.commerce.core.product;

import com.commerce.core.common.exception.CommerceException;
import com.commerce.core.product.entity.ProductStock;
import com.commerce.core.product.service.ProductStockService;
import com.commerce.core.product.vo.ProductStockDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ProductStockTest {

    @Autowired
    private ProductStockService productStockService;

    @Test
    void productStockAdjustment() {
        ProductStockDto dto = new ProductStockDto();
        dto.setProductSeq(1L);
        dto.setStock(5L);
        Long beforeStock = 0L;
        try {
            ProductStock productStock = productStockService.selectProductStock(dto).get();
            beforeStock = productStock.getStock();
        } catch (Exception e) {

        }
        ProductStock register = productStockService.add(dto);

        assertThat(register.getStock()).isEqualTo(beforeStock + dto.getStock());
    }
}
