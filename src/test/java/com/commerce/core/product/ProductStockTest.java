package com.commerce.core.product;

import com.commerce.core.common.exception.CommerceException;
import com.commerce.core.entity.ProductStock;
import com.commerce.core.service.product.ProductStockService;
import com.commerce.core.vo.product.ProductStockDto;
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
        dto.setStock(100L);
        Long beforeStock = 0L;
        try {
            ProductStock productStock = productStockService.selectProductStock(dto);
            beforeStock = productStock.getStock();
        } catch (CommerceException e) {

        }
        ProductStock register = productStockService.adjustment(dto);

        assertThat(register.getStock()).isEqualTo(beforeStock + dto.getStock());
    }
}
