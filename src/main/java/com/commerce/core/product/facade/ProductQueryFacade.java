package com.commerce.core.product.facade;

import com.commerce.core.common.exception.CommerceException;
import com.commerce.core.common.exception.ExceptionStatus;
import com.commerce.core.product.domain.ProductDao;
import com.commerce.core.product.domain.ProductOptionDao;
import com.commerce.core.product.facade.response.ProductResponse.ProductOptionResponse;
import com.commerce.core.product.facade.response.ProductResponse;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ProductQueryFacade {
    private final ProductDao productDao;
    private final ProductOptionDao productOptionDao;

    public ProductQueryFacade(ProductDao productDao,
                              ProductOptionDao productOptionDao
    ) {
        this.productDao = productDao;
        this.productOptionDao = productOptionDao;
    }

    @Transactional(readOnly = true)
    public ProductResponse getProduct(Long productSeq) {
        var optionalProduct = productDao.findById(productSeq);
        if (optionalProduct.isEmpty()) throw new CommerceException(ExceptionStatus.ENTITY_IS_EMPTY);

        var product = optionalProduct.get();
        var list = productOptionDao.findByProduct(product).stream()
                .map(item -> new ProductOptionResponse(item.getProductOptionSeq(), item.getProductOptionName(), item.getProductOptionName(), item.getPrice(), item.getStock()))
                .toList();
        return ProductResponse.builder()
                .productSeq(product.getProductSeq())
                .productName(product.getProductName())
                .productDescription(product.getDescription())
                .productOptionList(list)
                .build();
    }

}
