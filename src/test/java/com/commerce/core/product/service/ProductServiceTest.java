package com.commerce.core.product.service;

import com.commerce.core.product.domain.ProductDao;
import com.commerce.core.product.domain.entity.ProductInfo;
import com.commerce.core.product.service.request.ProductServiceRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("상품 서비스")
class ProductServiceTest {

    @Mock
    ProductDao productDao;
    @Mock
    ApplicationEventPublisher publisher;

    ProductService productService;

    @BeforeEach
    void setUp() {
        productService = new ProductService(productDao, publisher);
    }

    @Test
    @DisplayName("상품정보등록")
    void 상품정보등록_성공() {
        // given
        ProductServiceRequest productDto = ProductServiceRequest.builder()
                .productInfoSeq(1L)
                .productOptions(List.of("A", "B"))
                .productDetail("productDetail")
                .productName("productName")
                .price(1000L)
                .build();

        ProductInfo productInfo = productDto.requestToEntity();
        when(productDao.findProductInfoById(any()))
                .thenReturn(Optional.of(productInfo));

        // when
        ProductInfo result = productService.add(productDto);

        // then
        assertThat(result.getPrice()).isEqualTo(productDto.price());
        assertThat(result.getProductName()).isEqualTo(productDto.productName());
        assertThat(result.getProductDetail()).isEqualTo(productDto.productDetail());
        assertThat(result.getProductInfoSeq()).isEqualTo(productDto.productInfoSeq());
    }
}