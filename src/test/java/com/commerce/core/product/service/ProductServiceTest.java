package com.commerce.core.product.service;

import com.commerce.core.event.producer.EventSender;
import com.commerce.core.product.domain.ProductDao;
import com.commerce.core.product.domain.entity.ProductInfo;
import com.commerce.core.product.vo.ProductDto;
import com.commerce.core.product.vo.ProductResDto;
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
        productService = new ProductServiceImpl(productDao, eventSender);
    }

    @Test
    @DisplayName("상품정보등록")
    void 상품정보등록_성공() {
        // given
        ProductDto productDto = new ProductDto();
        productDto.setProductInfoSeq(1L);
        productDto.setProductOptions(List.of("A", "B"));
        productDto.setProductDetail("productDetail");
        productDto.setProductName("productName");
        productDto.setPrice(1000L);

        ProductInfo productInfo = productDto.dtoToEntity();
        Mockito.when(productDao.productInfoFindById(Mockito.any()))
                .thenReturn(Optional.of(productInfo));

        // when
        ProductResDto result = productService.add(productDto);

        // then
        assertThat(result.getPrice()).isEqualTo(productDto.getPrice());
        assertThat(result.getProductName()).isEqualTo(productDto.getProductName());
        assertThat(result.getProductDetail()).isEqualTo(productDto.getProductDetail());
        assertThat(result.getProductInfoSeq()).isEqualTo(productDto.getProductInfoSeq());
    }
}