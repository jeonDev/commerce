package com.commerce.core.product;

import com.commerce.core.entity.Product;
import com.commerce.core.service.product.ProductService;
import com.commerce.core.vo.product.ProductDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest

public class ProductTest {

    @Autowired
    private ProductService productService;

    @Test
    void productAddTest() {
        String productName = "BB";
        ProductDto dto = new ProductDto();
        dto.setProductName(productName);
        Product result = productService.add(dto);

        assertThat(productName).isEqualTo(productService.selectProduct(result.entityToDto()).getProductName());
    }
}
