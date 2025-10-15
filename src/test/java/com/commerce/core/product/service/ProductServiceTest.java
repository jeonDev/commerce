package com.commerce.core.product.service;

import com.commerce.core.event.producer.EventSender;
import com.commerce.core.product.domain.ProductDao;
import com.commerce.core.product.domain.entity.ProductInfo;
import com.commerce.core.product.service.request.ProductServiceRequest;
import com.commerce.core.product.service.response.ProductServiceResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@DisplayName("상품 서비스")
class ProductServiceTest {

    @Mock
    ProductDao productDao;
    @Mock
    EventSender eventSender;

    ProductService productService;

    @BeforeEach
    void setUp() {
        productService = new ProductService(productDao, eventSender);
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
        Mockito.when(productDao.findProductInfoById(Mockito.any()))
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