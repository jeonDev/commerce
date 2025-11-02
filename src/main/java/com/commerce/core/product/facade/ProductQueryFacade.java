package com.commerce.core.product.facade;

import com.commerce.core.common.type.PageListResponse;
import com.commerce.core.product.domain.ProductDao;
import com.commerce.core.product.service.response.AdminProductListServiceResponse;
import com.commerce.core.product.service.response.ProductDetailServiceResponse;
import com.commerce.core.product.service.response.ProductOrderServiceResponse;
import com.commerce.core.product.service.response.ProductViewServiceResponse;
import com.commerce.core.product.type.ProductOptions;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ProductQueryFacade {
    private final ProductDao productDao;

    public ProductQueryFacade(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Transactional(readOnly = true)
    public ProductDetailServiceResponse selectProductViewDetail(Long productInfoSeq) {
//        var productInfo = productDao.selectProductDetail(productInfoSeq);
//        var productOptions = productDao.findByProductInfoSeq(productInfoSeq).stream()
//                .map(option -> ProductOptions.builder()
//                        .productSeq(option.getProductSeq())
//                        .productOption(option.getProductOptionCode())
//                        .build())
//                .toList();
        return ProductDetailServiceResponse.builder()
//                .productInfoSeq(productInfo.getProductInfoSeq())
//                .productName(productInfo.getProductName())
//                .productDetail(productInfo.getProductDetail())
//                .price(productInfo.getPrice())
//                .productOptions(productOptions)
                .build();
    }

    @Transactional(readOnly = true)
    public ProductOrderServiceResponse selectProductView(Long productSeq) {
//        var product = productDao.selectProduct(productSeq);
        return ProductOrderServiceResponse.builder()
//                .productSeq(product.getProductSeq())
//                .productOptionCode(product.getProductOptionCode())
//                .productInfoSeq(product.getProductInfoSeq())
//                .productName(product.getProductName())
//                .productDetail(product.getProductDetail())
//                .price(product.getPrice())
                .build();
    }

    @Transactional(readOnly = true)
    public PageListResponse<ProductViewServiceResponse> selectProductViewList(int pageNumber, int pageSize) {
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
