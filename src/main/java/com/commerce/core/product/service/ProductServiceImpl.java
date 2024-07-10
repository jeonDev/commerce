package com.commerce.core.product.service;

import com.commerce.core.common.exception.CommerceException;
import com.commerce.core.common.exception.ExceptionStatus;
import com.commerce.core.event.EventTopic;
import com.commerce.core.event.producer.EventSender;
import com.commerce.core.product.entity.Product;
import com.commerce.core.product.entity.ProductInfo;
import com.commerce.core.product.repository.ProductInfoRepository;
import com.commerce.core.product.repository.ProductRepository;
import com.commerce.core.product.vo.ProductDto;
import com.commerce.core.product.vo.ProductResDto;
import com.commerce.core.product.vo.ProductViewDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductInfoRepository productInfoRepository;


    private final EventSender eventSender;

    @Transactional
    @Override
    public ProductResDto add(ProductDto dto) {
        // 1. 상품 정보 조회
        ProductInfo productInfo = this.mergeProductInfo(dto);

        // 2. 상품 save
        List<Product> list = dto.getProductOptions()
                .stream()
                .map(s -> Product.builder()
                        .productInfo(productInfo)
                        .productOptionCode(s)
                        .build())
                .toList();
        List<Product> products = productRepository.saveAll(list);

        // 3. Event Producer Push
        ProductViewDto productViewDto = ProductViewDto.builder()
                .productInfoSeq(productInfo.getProductInfoSeq())
                .productViewStatus(ProductViewDto.ProductViewStatus.REGISTER)
                .build();
        eventSender.send(EventTopic.SYNC_PRODUCT.getTopic(), productViewDto);

        return ProductResDto.builder()
                .productInfoSeq(productInfo.getProductInfoSeq())
                .productName(productInfo.getProductName())
                .productDetail(productInfo.getProductDetail())
                .price(productInfo.getPrice())
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Product> selectProduct(Long productSeq) {
        return productRepository.findById(productSeq);
    }

    private ProductInfo mergeProductInfo(ProductDto dto) {
        // 1-1. 상품 정보 존재 시, 세팅
        Long productInfoSeq = dto.getProductInfoSeq();
        if(productInfoSeq == null) {
            return this.productInfoAdd(dto);
        }
        // 1-2. 상품 정보 없을 시, 등록 및 세팅
        ProductInfo productInfo = this.selectProductInfo(productInfoSeq)
                .orElseThrow(() -> new CommerceException(ExceptionStatus.ENTITY_IS_EMPTY));
        productInfo.update(dto.getProductName(), dto.getProductDetail(), dto.getPrice());
        return productInfo;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Product> selectProductToProductInfo(Long productInfoSeq) {
        return productRepository.findByProductInfo_ProductInfoSeq(productInfoSeq);
    }


    @Transactional
    @Override
    public ProductInfo productInfoAdd(ProductDto dto) {
        return productInfoRepository.save(dto.dtoToEntity());
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<ProductInfo> selectProductInfo(Long productInfoSeq) {
        return productInfoRepository.findById(productInfoSeq);
    }
}
