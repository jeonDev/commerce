package com.commerce.core.product;

import com.commerce.core.product.entity.ProductStock;
import com.commerce.core.product.service.ProductStockService;
import com.commerce.core.product.vo.ProductStockDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
public class ProductStockTest {

    final int TEST_THREAD_COUNT = 10;
    @Autowired
    private ProductStockService productStockService;

    @Test
    void productStockAdjustment() {
        ProductStockDto dto = ProductStockDto.builder()
                .productSeq(1L)
                .stock(5L)
                .build();
        Long beforeStock = 0L;
        try {
            beforeStock = productStockService.selectProductStock(dto).get().getStock();
        } catch (Exception e) {

        }
        ProductStock register = productStockService.add(dto);

        assertThat(register.getStock()).isEqualTo(beforeStock + dto.getStock());
    }

    @Test
    void productStockRedissonLockTest() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(TEST_THREAD_COUNT);
        CountDownLatch latch = new CountDownLatch(TEST_THREAD_COUNT);

        for(int i = 0; i < TEST_THREAD_COUNT; i++) {
            ProductStockDto dto = ProductStockDto.builder()
                    .productSeq(2L)
                    .stock(1L)
                    .build();

            int cnt = i;
            executorService.execute(() -> {
                try {
                    if(cnt % 2 == 0) productStockService.add(dto);
                    else productStockService.consume(dto);
                } catch (Exception e) {
                    log.error("error : {}", e);
                }
                latch.countDown();
            });
        }

        latch.await();
    }
}
