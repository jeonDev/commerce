package com.commerce.core.product.service;

import com.commerce.core.product.entity.Product;
import com.commerce.core.product.entity.ProductInfo;
import com.commerce.core.product.entity.ProductStock;
import com.commerce.core.product.entity.mongo.ProductView;
import com.commerce.core.product.repository.dsl.ProductDslRepository;
import com.commerce.core.product.repository.mongo.ProductViewRepository;
import com.commerce.core.product.vo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductViewServiceImpl implements ProductViewService {

    private final ProductViewRepository productViewRepository;

    private final ProductService productService;
    private final ProductStockService productStockService;
    private final ProductDslRepository productDslRepository;

    @Transactional
    @Override
    public void merge(ProductViewDto dto) {
        log.info("Event Request : {} {} ", dto.getProductInfoSeq(), dto.getProductViewStatus());

        switch (dto.getProductViewStatus()) {
            case REGISTER:
                this.productViewUpdate(dto.getProductInfoSeq());
                break;
            case STOCK_ADJUSTMENT:
                this.productViewStockUpdate(dto.getProductInfoSeq());
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
        List<String> productOptions = productService.selectProductToProductInfo(productInfo.getProductInfoSeq())
                .stream()
                .map(Product::getProductOptionCode)
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

    private void productViewMerge(ProductInfo productInfo, ProductStockSummary productStockSummary, List<String> productOptions) {
        selectProductViewForProductDetail(productInfo.getProductInfoSeq())
                .ifPresentOrElse(item -> {
                    item.productViewSyncUpdate(productInfo.getProductInfoSeq(),
                            productInfo.getProductName(),
                            productInfo.getProductDetail(),
                            productInfo.getPrice(),
                            "Y",
                            productOptions,
                            productStockSummary);
                    productViewRepository.save(item);
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
                    productViewRepository.save(productView);
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
        return productViewRepository.findByProductInfoSeq(productDetailSeq);
    }

    @Transactional(readOnly = true)
    @Override
    public ProductDetailDto selectProductViewDetail(Long productInfoSeq) {
        ProductInfo productInfo = productDslRepository.selectProductDetail(productInfoSeq);
        List<String> productOptions = productService.selectProductToProductInfo(productInfoSeq).stream()
                .map(Product::getProductOptionCode)
                .toList();
        return ProductDetailDto.builder()
                .productInfoSeq(productInfo.getProductInfoSeq())
                .productName(productInfo.getProductName())
                .productDetail(productInfo.getProductDetail())
                .price(productInfo.getPrice())
                .productOptions(productOptions)
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductViewResDto> selectProductViewList() {
        return productViewRepository.findAll().stream()
                .map(ProductView::documentToResDto)
                .toList();
    }
}
