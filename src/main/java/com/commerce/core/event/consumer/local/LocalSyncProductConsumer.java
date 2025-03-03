package com.commerce.core.event.consumer.local;

import com.commerce.core.product.service.ProductViewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile("test")
public class LocalSyncProductConsumer {
    private final ProductViewService productViewService;

    public LocalSyncProductConsumer(ProductViewService productViewService) {
        this.productViewService = productViewService;
    }

}
