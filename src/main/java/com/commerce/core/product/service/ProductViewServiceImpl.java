package com.commerce.core.product.service;

import com.commerce.core.common.vo.PageListVO;
import com.commerce.core.product.domain.ProductDao;
import com.commerce.core.product.domain.entity.Product;
import com.commerce.core.product.domain.entity.ProductInfo;
import com.commerce.core.product.domain.entity.ProductStock;
import com.commerce.core.product.domain.entity.mongo.ProductView;
import com.commerce.core.product.domain.repository.dsl.vo.ProductDAO;
import com.commerce.core.product.service.request.ProductViewServiceRequest;
import com.commerce.core.product.service.response.ProductDetailServiceResponse;
import com.commerce.core.product.service.response.ProductOrderServiceResponse;
import com.commerce.core.product.vo.*;
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
@RequiredArgsConstructor
@Service
public class ProductViewServiceImpl implements ProductViewService {

    private final ProductDao productDao;

    private final ProductService productService;
    private final ProductStockService productStockService;

    @Transactional
    @Override
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
        ProductInfo productInfo = productService.selectProductInfo(productInfoSeq).orElseThrow();
        ProductStockSummary productStockSummary = this.makingProductStockSummary(productInfo.getProducts());
        List<ProductOptions> productOptions = productService.selectProductToProductInfo(productInfo.getProductInfoSeq())
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
        ProductInfo productInfo = productService.selectProductInfo(productInfoSeq).orElseThrow();
        ProductStockSummary productStockSummary = this.makingProductStockSummary(productInfo.getProducts());

        // 2. View Merge
        this.productViewMerge(productInfo, productStockSummary, null);
    }

    private void productViewMerge(ProductInfo productInfo, ProductStockSummary productStockSummary, List<ProductOptions> productOptions) {
        selectProductViewForProductDetail(productInfo.getProductInfoSeq())
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
                    Optional<ProductStock> optionalProductStock = productStockService.selectProductStock(product.getProductSeq());
                    if (optionalProductStock.isPresent()) {
                        return optionalProductStock.get().getStock();
                    }
                    return 0L;
                })
                .sum();

        return productStockService.productStockSummary(stock);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<ProductView> selectProductViewForProductDetail(Long productDetailSeq) {
        return productDao.productViewFindByProductInfoSeq(productDetailSeq);
    }

    @Transactional(readOnly = true)
    @Override
    public ProductDetailServiceResponse selectProductViewDetail(Long productInfoSeq) {
        ProductInfo productInfo = productDao.selectProductDetail(productInfoSeq);
        List<ProductOptions> productOptions = productService.selectProductToProductInfo(productInfoSeq).stream()
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

    @Override
    public ProductOrderServiceResponse selectProductView(Long productSeq) {
        ProductDAO product = productDao.selectProduct(productSeq);
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
    @Override
    public PageListVO<ProductViewResDto> selectProductViewList(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<ProductView> list = productDao.productViewFindAll(pageable);
        return PageListVO.<ProductViewResDto>builder()
                .list(list.getContent().stream()
                        .map(ProductView::documentToResDto)
                        .toList()
                )
                .totalPage(list.getTotalPages())
                .build();
    }

    @Override
    public PageListVO<AdminProductListResDto> selectProductList(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<AdminProductListResDto> list = productDao.selectProductList(pageable);
        return PageListVO.<AdminProductListResDto>builder()
                .list(list.get().toList())
                .totalPage(list.getTotalPages())
                .build();
    }
}
