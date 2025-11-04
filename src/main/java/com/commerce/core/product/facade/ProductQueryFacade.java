package com.commerce.core.product.facade;

import com.commerce.core.common.exception.CommerceException;
import com.commerce.core.common.exception.ExceptionStatus;
import com.commerce.core.common.type.PageListResponse;
import com.commerce.core.product.domain.ProductDao;
import com.commerce.core.product.domain.ProductOptionDao;
import com.commerce.core.product.facade.response.ProductResponse.ProductOptionResponse;
import com.commerce.core.product.facade.response.AdminProductListServiceResponse;
import com.commerce.core.product.facade.response.ProductResponse;
import com.commerce.core.product.facade.response.ProductViewServiceResponse;
import org.springframework.data.domain.PageRequest;
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

    @Transactional(readOnly = true)
    public PageListResponse<ProductViewServiceResponse> selectProductViewList(int pageNumber, int pageSize) {
        // TODO:
        var pageable = PageRequest.of(pageNumber, pageSize);
//        var list = productDao.productViewFindAll(pageable);
        return PageListResponse.<ProductViewServiceResponse>builder()
//                .list(list.getContent().stream()
//                        .map(ProductViewServiceResponse::from)
//                        .toList()
//                )
//                .totalPage(list.getTotalPages())
                .build();
    }

    @Transactional(readOnly = true)
    public PageListResponse<AdminProductListServiceResponse> selectProductList(int pageNumber, int pageSize) {
        // TODO:
        var pageable = PageRequest.of(pageNumber, pageSize);
//        var list = productDao.selectProductList(pageable);
        return PageListResponse.<AdminProductListServiceResponse>builder()
//                .list(list.stream()
//                        .map(AdminProductListServiceResponse::from)
//                        .toList()
//                )
//                .totalPage(list.getTotalPages())
                .build();
    }
}
