package com.commerce.core.product.service;

import com.commerce.core.product.domain.ProductDao;
import com.commerce.core.product.domain.ProductStockDao;
import com.commerce.core.product.domain.entity.Product;
import com.commerce.core.product.domain.entity.ProductInfo;
import com.commerce.core.product.domain.entity.ProductStock;
import com.commerce.core.product.domain.entity.ProductStockHistory;
import com.commerce.core.product.service.request.ProductStockServiceRequest;
import com.commerce.core.product.type.ProductStockProcessStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("상품 재고 서비스")
class ProductStockServiceTest {

    @Mock
    ProductStockDao productStockDao;
    @Mock
    ProductDao productDao;
    @Mock
    ApplicationEventPublisher publisher;

    ProductStockService productStockService;

    @BeforeEach
    void setUp() {
        productStockService = new ProductStockService(productStockDao,
                productDao, publisher);
    }

    @Test
    @DisplayName("상품재고 추가")
    void 상품재고추가_성공() {
        // given
        ProductStockServiceRequest productStockRequest = ProductStockServiceRequest.builder()
                .productSeq(1L)
                .stock(2L)
                .productStockProcessStatus(ProductStockProcessStatus.ADD)
                .build();

        ProductInfo productInfo = ProductInfo.builder()
                .productInfoSeq(1L)
                .build();
        Product product = Product.builder()
                .productSeq(1L)
                .productOptionCode("A")
                .productInfo(productInfo)
                .build();
        when(productDao.findById(any()))
                .thenReturn(Optional.of(product));

        ProductStock productStock = ProductStock.builder()
                .stock(1L)
                .product(product)
                .build();
        when(productStockDao.lockFindById(anyLong()))
                .thenReturn(Optional.of(productStock));

        doNothing().when(publisher).publishEvent(any());

        // when
        ProductStockHistory result = productStockService.productStockAdjustment(productStockRequest);

        // then
        assertThat(result.getStock()).isEqualTo(2L);
    }
}