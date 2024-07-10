package com.commerce.core.product;

import com.commerce.core.product.entity.Product;
import com.commerce.core.product.service.ProductService;
import com.commerce.core.product.vo.ProductDto;
import com.commerce.core.product.vo.ProductInfoDto;
import com.commerce.core.product.vo.ProductResDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        Long price = 10000L;
        ProductDto dto = new ProductDto();
        dto.setProductName(productName);
        dto.setProductDetail(productDetail);
        dto.setPrice(price);
        List<String> productOptionCode = List.of("SMALL", "LARGE");
        dto.setProductOptions(productOptionCode);

        ProductResDto result = productService.add(dto);


        assertThat(productName).isEqualTo(productService.selectProductInfo(result.getProductInfoSeq()).get().getProductName());
    }
}
