package com.commerce.core.product.service;

import com.commerce.core.common.exception.CommerceException;
import com.commerce.core.common.exception.ExceptionStatus;
import com.commerce.core.product.domain.ProductOptionDao;
import com.commerce.core.product.domain.ProductStockHistoryDao;
import com.commerce.core.product.domain.entity.ProductStockHistory;
import com.commerce.core.product.domain.type.ProductStockProcessStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class ProductStockService {

    private final ProductOptionDao productOptionDao;
    private final ProductStockHistoryDao productStockHistoryDao;

    public ProductStockService(ProductOptionDao productOptionDao,
                               ProductStockHistoryDao productStockHistoryDao) {
        this.productOptionDao = productOptionDao;
        this.productStockHistoryDao = productStockHistoryDao;
    }

    @Transactional
    public ProductStockHistory productStockAdjustment(Long productOptionSeq, ProductStockProcessStatus productStockProcessStatus, Long stock) {
        var productOption = productOptionDao.findByIdForUpdate(productOptionSeq)
                .orElseThrow(() -> new CommerceException(ExceptionStatus.ENTITY_IS_EMPTY));

        var productStockHistory = productOption.adjustment(productStockProcessStatus, stock);
        productOptionDao.save(productOption);
        productStockHistoryDao.save(productStockHistory);

        return productStockHistory;
    }

}
