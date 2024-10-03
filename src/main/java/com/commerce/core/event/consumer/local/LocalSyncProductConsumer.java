package com.commerce.core.event.consumer.local;

import com.commerce.core.event.consumer.AbstractEventConsumer;
import com.commerce.core.product.service.ProductViewService;
import com.commerce.core.product.vo.ProductViewDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile("test")
public class LocalSyncProductConsumer extends AbstractEventConsumer<ProductViewDto> {
    private final ProductViewService productViewService;

    public LocalSyncProductConsumer(ProductViewService productViewService) {
        this.productViewService = productViewService;
    }

    @Override
    public void listener(Object data) {
        eventExecuteTemplate(data, ProductViewDto.class);
    }

    @Override
    public void eventProcess(ProductViewDto data) {
        productViewService.merge(data);
    }
}
