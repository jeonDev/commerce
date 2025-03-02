package com.commerce.core.product.domain;

import com.commerce.core.product.domain.entity.Product;
import com.commerce.core.product.domain.entity.ProductInfo;
import com.commerce.core.product.domain.entity.mongo.ProductView;
import com.commerce.core.product.domain.repository.ProductInfoRepository;
import com.commerce.core.product.domain.repository.ProductRepository;
import com.commerce.core.product.domain.repository.dsl.ProductDslRepository;
import com.commerce.core.product.domain.repository.dsl.vo.ProductDAO;
import com.commerce.core.product.domain.repository.mongo.ProductViewRepository;
import com.commerce.core.product.vo.AdminProductListResDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductDaoImpl implements ProductDao {

    private final ProductRepository productRepository;
    private final ProductInfoRepository productInfoRepository;
    private final ProductViewRepository productViewRepository;
    private final ProductDslRepository productDslRepository;

    public ProductDaoImpl(ProductRepository productRepository,
                          ProductInfoRepository productInfoRepository,
                          ProductViewRepository productViewRepository,
                          ProductDslRepository productDslRepository) {
        this.productRepository = productRepository;
        this.productInfoRepository = productInfoRepository;
        this.productViewRepository = productViewRepository;
        this.productDslRepository = productDslRepository;
    }

    @Override
    public List<Product> saveAll(List<Product> products) {
        return productRepository.saveAll(products);
    }

    @Override
    public Optional<Product> findById(Long productSeq) {
        return productRepository.findById(productSeq);
    }

    @Override
    public List<Product> findByProductInfoSeq(Long productInfoSeq) {
        return productRepository.findByProductInfo_ProductInfoSeq(productInfoSeq);
    }

    @Override
    public ProductInfo productInfoSave(ProductInfo productInfo) {
        return productInfoRepository.save(productInfo);
    }

    @Override
    public Optional<ProductInfo> findProductInfoById(Long productInfoSeq) {
        return productInfoRepository.findById(productInfoSeq);
    }

    @Override
    public void productViewSave(ProductView productView) {
        productViewRepository.save(productView);
    }

    @Override
    public Optional<ProductView> productViewFindByProductInfoSeq(Long productDetailSeq) {
        return productViewRepository.findByProductInfoSeq(productDetailSeq);
    }

    @Override
    public ProductInfo selectProductDetail(Long productInfoSeq) {
        return productDslRepository.selectProductDetail(productInfoSeq);
    }

    @Override
    public ProductDAO selectProduct(Long productSeq) {
        return productDslRepository.selectProduct(productSeq);
    }

    @Override
    public Page<ProductView> productViewFindAll(Pageable pageable) {
        return productViewRepository.findAll(pageable);
    }

    @Override
    public Page<AdminProductListResDto> selectProductList(Pageable pageable) {
        return productDslRepository.selectProductList(pageable);
    }
}
