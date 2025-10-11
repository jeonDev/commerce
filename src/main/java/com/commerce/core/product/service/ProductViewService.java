package com.commerce.core.product.service;

import com.commerce.core.common.type.PageListResponse;
import com.commerce.core.product.domain.ProductDao;
import com.commerce.core.product.domain.ProductStockDao;
import com.commerce.core.product.domain.entity.Product;
import com.commerce.core.product.domain.entity.ProductInfo;
import com.commerce.core.product.domain.entity.ProductStock;
import com.commerce.core.product.domain.entity.mongo.ProductView;
import com.commerce.core.product.domain.repository.dsl.dto.ProductDto;
import com.commerce.core.product.service.request.ProductViewServiceRequest;
import com.commerce.core.product.domain.dto.AdminProductListDto;
import com.commerce.core.product.service.response.AdminProductListServiceResponse;
import com.commerce.core.product.service.response.ProductDetailServiceResponse;
import com.commerce.core.product.service.response.ProductOrderServiceResponse;
import com.commerce.core.product.service.response.ProductViewServiceResponse;
import com.commerce.core.product.type.ProductOptions;
import com.commerce.core.product.type.ProductStockSummary;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ProductViewService {

    private final ProductDao productDao;
    private final ProductStockDao productStockDao;

    public ProductViewService(ProductDao productDao,
                              ProductStockDao productStockDao) {
        this.productDao = productDao;
        this.productStockDao = productStockDao;
    }

    @Transactional
    public void merge(ProductViewServiceRequest request) {
        log.info("Event Request : {} {} ", request.productInfoSeq(), request.productViewStatus());

        switch (request.productViewStatus()) {
            case REGISTER:
                this.productViewUpdate(request.productInfoSeq());
                break;
            case STOCK_ADJUSTMENT:
                this.productViewStockUpdate(request.productInfoSeq());
                break;
            default:
                log.error("ProductViewStatus 매칭 실패");
                break;
        }
    }

    private void productViewUpdate(Long productInfoSeq) {
        // 1. 기존 데이터 존재 여부 체크
        var productInfo = productDao.findProductInfoById(productInfoSeq)
                .orElseThrow();
        var productStockSummary = this.makingProductStockSummary(productInfo.getProducts());
        var productOptions = productDao.findByProductInfoSeq(productInfo.getProductInfoSeq())
                .stream()
                .map(option -> ProductOptions.builder()
                        .productSeq(option.getProductSeq())
                        .productOption(option.getProductOptionCode())
                        .build())
                .toList();

        // 2. View Merge
        this.productViewMerge(productInfo, productStockSummary, productOptions);
    }

    private void productViewStockUpdate(Long productInfoSeq) {
        // 1. 기존 데이터 존재 여부 체크
        var productInfo = productDao.findProductInfoById(productInfoSeq)
                .orElseThrow();
        var productStockSummary = this.makingProductStockSummary(productInfo.getProducts());

        // 2. View Merge
        this.productViewMerge(productInfo, productStockSummary, null);
    }

    private void productViewMerge(ProductInfo productInfo, ProductStockSummary productStockSummary, List<ProductOptions> productOptions) {
        productDao.productViewFindByProductInfoSeq(productInfo.getProductInfoSeq())
                .ifPresentOrElse(item -> {
                    item.productViewSyncUpdate(productInfo.getProductInfoSeq(),
                            productInfo.getProductName(),
                            productInfo.getProductDetail(),
                            productInfo.getPrice(),
                            "Y",
                            productOptions,
                            productStockSummary);
                    productDao.productViewSave(item);
                }, () -> {
                    ProductView productView = ProductView.builder()
                            .productInfoSeq(productInfo.getProductInfoSeq())
                            .productName(productInfo.getProductName())
                            .productDetail(productInfo.getProductDetail())
                            .price(productInfo.getPrice())
                            .useYn("Y")
                            .productOptions(productOptions)
                            .productStockSummary(productStockSummary)
                            .build();
                    productDao.productViewSave(productView);
                });
    }

    private ProductStockSummary makingProductStockSummary(List<Product> productList) {
        Long stock = productList.stream()
                .mapToLong(product -> {
                    Optional<ProductStock> optionalProductStock = productStockDao.productStockFindById(product.getProductSeq());
                    if (optionalProductStock.isPresent()) {
                        return optionalProductStock.get().getStock();
                    }
                    return 0L;
                })
                .sum();

        return ProductStockSummary.of(stock);
    }

    @Transactional(readOnly = true)
    public ProductDetailServiceResponse selectProductViewDetail(Long productInfoSeq) {
        var productInfo = productDao.selectProductDetail(productInfoSeq);
        var productOptions = productDao.findByProductInfoSeq(productInfoSeq).stream()
                .map(option -> ProductOptions.builder()
                        .productSeq(option.getProductSeq())
                        .productOption(option.getProductOptionCode())
                        .build())
                .toList();
        return ProductDetailServiceResponse.builder()
                .productInfoSeq(productInfo.getProductInfoSeq())
                .productName(productInfo.getProductName())
                .productDetail(productInfo.getProductDetail())
                .price(productInfo.getPrice())
                .productOptions(productOptions)
                .build();
    }

    public ProductOrderServiceResponse selectProductView(Long productSeq) {
        var product = productDao.selectProduct(productSeq);
        return ProductOrderServiceResponse.builder()
                .productSeq(product.getProductSeq())
                .productOptionCode(product.getProductOptionCode())
                .productInfoSeq(product.getProductInfoSeq())
                .productName(product.getProductName())
                .productDetail(product.getProductDetail())
                .price(product.getPrice())
                .build();
    }

    @Transactional(readOnly = true)
    public PageListResponse<ProductViewServiceResponse> selectProductViewList(int pageNumber, int pageSize) {
        var pageable = PageRequest.of(pageNumber, pageSize);
        var list = productDao.productViewFindAll(pageable);
        return PageListResponse.<ProductViewServiceResponse>builder()
                .list(list.getContent().stream()
                        .map(ProductViewServiceResponse::from)
                        .toList()
                )
                .totalPage(list.getTotalPages())
                .build();
    }

    @Transactional(readOnly = true)
    public PageListResponse<AdminProductListServiceResponse> selectProductList(int pageNumber, int pageSize) {
        var pageable = PageRequest.of(pageNumber, pageSize);
        var list = productDao.selectProductList(pageable);
        return PageListResponse.<AdminProductListServiceResponse>builder()
                .list(list.stream()
                        .map(AdminProductListServiceResponse::from)
                        .toList()
                )
                .totalPage(list.getTotalPages())
                .build();
    }
}
