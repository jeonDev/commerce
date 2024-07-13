package com.commerce.core.product.service;

import com.commerce.core.event.producer.EventSender;
import com.commerce.core.product.entity.Product;
import com.commerce.core.product.entity.ProductInfo;
import com.commerce.core.product.entity.ProductStock;
import com.commerce.core.product.repository.ProductStockHistoryRepository;
import com.commerce.core.product.repository.ProductStockRepository;
import com.commerce.core.product.vo.ProductStockDto;
import com.commerce.core.product.vo.ProductStockProcessStatus;
import com.commerce.core.product.vo.ProductViewDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("상품 재고 서비스")
class ProductStockServiceTest {

    @Mock
    ProductStockRepository productStockRepository;
    @Mock
    ProductStockHistoryRepository productStockHistoryRepository;
    @Mock
    ProductService productService;
    @Mock
    EventSender eventSender;

    ProductStockService productStockService;

    @BeforeEach
    void setUp() {
        productStockService = new ProductStockServiceImpl(productStockRepository,
                productStockHistoryRepository, productService, eventSender);
    }

    @Test
    @DisplayName("상품재고 추가")
    void 상품재고추가_성공() {
        // given
        ProductStockDto productStockDto = new ProductStockDto();
        productStockDto.setProductSeq(1L);
        productStockDto.setStock(1L);
        productStockDto.setProductStockProcessStatus(ProductStockProcessStatus.ADD);

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
        Mockito.when(productStockRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(productStock));

        Mockito.doNothing().when(eventSender).send(Mockito.any(), Mockito.any());

        // when
        ProductStock result = productStockService.productStockAdjustment(productStockDto);

        // then
        assertThat(result.getStock()).isEqualTo(2L);
    }
}