package com.commerce.core.product.service;

import com.commerce.core.product.domain.ProductDao;
import com.commerce.core.product.domain.entity.Product;
import com.commerce.core.product.domain.entity.ProductInfo;
import com.commerce.core.product.service.request.ProductViewServiceRequest;
import com.commerce.core.product.type.ProductViewStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@DisplayName("상품 정보 동기화 서비스")
class ProductViewServiceTest {

    @Mock
    ProductDao productDao;
    @Mock
    ProductService productService;
    @Mock
    ProductStockService productStockService;
    ProductViewService productViewService;

    @BeforeEach
    void setUp() {
        productViewService = new ProductViewService(productDao,
                productService, productStockService);
        ProductInfo productInfo = ProductInfo.builder()
                .products(List.of(Product.builder().productOptionCode("A").build()))
                .build();
        Mockito.when(productService.selectProductInfo(Mockito.anyLong()))
                .thenReturn(Optional.of(productInfo));
    }

    @Test
    @DisplayName("상품 등록 동기화")
    void 상품등록동기화_성공() {
        // given
        ProductViewServiceRequest request = ProductViewServiceRequest.builder()
                .productInfoSeq(1L)
                .productViewStatus(ProductViewStatus.REGISTER)
                .build();

        // when
        productViewService.merge(request);

        // then
    }

    @Test
    @DisplayName("상품 재고 동기화")
    void 상품재고동기화_성공() {
        // given
        ProductViewServiceRequest request = ProductViewServiceRequest.builder()
                .productInfoSeq(1L)
                .productViewStatus(ProductViewStatus.STOCK_ADJUSTMENT)
                .build();

        // when
        productViewService.merge(request);
        // then
    }
}