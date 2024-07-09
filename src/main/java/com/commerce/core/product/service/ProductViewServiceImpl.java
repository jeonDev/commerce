package com.commerce.core.product.service;

import com.commerce.core.product.entity.Product;
import com.commerce.core.product.entity.ProductInfo;
import com.commerce.core.product.entity.ProductStock;
import com.commerce.core.product.entity.mongo.ProductView;
import com.commerce.core.product.repository.dsl.ProductDslRepository;
import com.commerce.core.product.repository.mongo.ProductViewRepository;
import com.commerce.core.product.vo.ProductDetailDto;
import com.commerce.core.product.vo.ProductStockSummary;
import com.commerce.core.product.vo.ProductViewDto;
import com.commerce.core.product.vo.ProductViewResDto;
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
        log.info("Event Request : {} ", dto.getProductViewStatus());

        // 1. 기존 데이터 존재여부 체크
        Product product = productService.selectProduct(dto.getProductSeq()).orElseThrow();
        ProductInfo productInfo = product.getProductInfo();
        ProductStockSummary productStockSummary = this.makingProductStockSummary(product.getProductSeq());
        List<String> productOptions = productService.selectProductToProductInfo(productInfo.getProductInfoSeq())
                .stream()
                .map(Product::getProductOptionCode)
                .toList();

        this.selectProductViewForProductDetail(productInfo.getProductInfoSeq())
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

    private ProductStockSummary makingProductStockSummary(Long productSeq) {
        Long stock = 0L;

        Optional<ProductStock> productStockOptional = productStockService.selectProductStock(productSeq);
        if(productStockOptional.isPresent()) {
            ProductStock productStock = productStockOptional.get();
            stock = productStock.getStock();
        }

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
        return ProductDetailDto.builder()
                .productInfoSeq(productInfo.getProductInfoSeq())
                .productName(productInfo.getProductName())
                .productDetail(productInfo.getProductDetail())
                .price(productInfo.getPrice())
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
