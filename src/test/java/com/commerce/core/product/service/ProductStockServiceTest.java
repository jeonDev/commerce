package com.commerce.core.product.service;

import com.commerce.core.event.EventTopic;
import com.commerce.core.event.producer.EventSender;
import com.commerce.core.product.domain.ProductStockDao;
import com.commerce.core.product.domain.entity.Product;
import com.commerce.core.product.domain.entity.ProductInfo;
import com.commerce.core.product.domain.entity.ProductStock;
import com.commerce.core.product.service.request.ProductStockServiceRequest;
import com.commerce.core.product.type.ProductStockProcessStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@DisplayName("상품 재고 서비스")
class ProductStockServiceTest {

    @Mock
    ProductStockDao productStockDao;
    @Mock
    ProductService productService;
    @Mock
    EventSender eventSender;

    ProductStockService productStockService;

    @BeforeEach
    void setUp() {
        productStockService = new ProductStockServiceImpl(productStockDao,
                productService, eventSender);
    }

    @Test
    @DisplayName("상품재고 추가")
    void 상품재고추가_성공() {
        // given
        ProductStockServiceRequest productStockRequest = ProductStockServiceRequest.builder()
                .productSeq(1L)
                .stock(1L)
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
        Mockito.when(productService.selectProduct(Mockito.any()))
                .thenReturn(Optional.of(product));

        ProductStock productStock = ProductStock.builder()
                .stock(1L)
                .product(product)
                .build();
        Mockito.when(productStockDao.productStockFindById(Mockito.anyLong()))
                .thenReturn(Optional.of(productStock));

        Mockito.doNothing().when(eventSender).send(Mockito.any(EventTopic.class), Mockito.any());

        // when
        ProductStock result = productStockService.productStockAdjustment(productStockRequest);

        // then
        assertThat(result.getStock()).isEqualTo(2L);
    }
}