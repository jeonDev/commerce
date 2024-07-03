package com.commerce.core.product;

import com.commerce.core.product.entity.Product;
import com.commerce.core.product.service.ProductService;
import com.commerce.core.product.vo.ProductDto;
import com.commerce.core.product.vo.ProductInfoDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest

public class ProductTest {

    @Autowired
    private ProductService productService;

    @Test
    @Transactional
    void productAddTest() {
        String productName = "AA";
        String productDetail = "테스트 상품A";
        String productOptionCode = "LARGE";
        ProductDto dto = new ProductDto();
        dto.setProductName(productName);
        ProductInfoDto infoDto = ProductInfoDto.builder()
                .productName(productName)
                .productDetail(productDetail)
                .price(10000L)
                .build();
        dto.setProductInfoDto(infoDto);
        dto.setProductOptionCode(productOptionCode);

        Product result = productService.add(dto);

        assertThat(productOptionCode).isEqualTo(productService.selectProduct(result.getProductSeq()).get().getProductOptionCode());
    }
}
