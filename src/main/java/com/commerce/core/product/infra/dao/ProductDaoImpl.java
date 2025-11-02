package com.commerce.core.product.infra.dao;

import com.commerce.core.product.domain.ProductDao;
import com.commerce.core.product.domain.entity.Product;
import com.commerce.core.product.infra.dao.jpa.ProductJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ProductDaoImpl implements ProductDao {

    private final ProductJpaRepository productRepository;

    public ProductDaoImpl(ProductJpaRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Optional<Product> findById(Long productSeq) {
        return productRepository.findById(productSeq);
    }


    //    private final ProductInfoRepository productInfoRepository;
//    private final ProductViewRepository productViewRepository;
//    private final ProductDslRepository productDslRepository;
//
//    public ProductDaoImpl(ProductRepository productRepository,
//                          ProductInfoRepository productInfoRepository,
//                          ProductViewRepository productViewRepository,
//                          ProductDslRepository productDslRepository) {
//        this.productRepository = productRepository;
//        this.productInfoRepository = productInfoRepository;
//        this.productViewRepository = productViewRepository;
//        this.productDslRepository = productDslRepository;
//    }
//
//    @Override
//    public List<Product> saveAll(List<Product> products) {
//        return productRepository.saveAll(products);
//    }
//
//    @Override
//    public Optional<Product> findById(Long productSeq) {
//        return productRepository.findById(productSeq);
//    }
//
//    @Override
//    public List<Product> findByProductInfoSeq(Long productInfoSeq) {
//        return productRepository.findByProductInfo_ProductInfoSeq(productInfoSeq);
//    }
//
//    @Override
//    public ProductInfo productInfoSave(ProductInfo productInfo) {
//        return productInfoRepository.save(productInfo);
//    }
//
//    @Override
//    public Optional<ProductInfo> findProductInfoById(Long productInfoSeq) {
//        return productInfoRepository.findById(productInfoSeq);
//    }
//
//    @Override
//    public void productViewSave(ProductView productView) {
//        productViewRepository.save(productView);
//    }
//
//    @Override
//    public Optional<ProductView> productViewFindByProductInfoSeq(Long productDetailSeq) {
//        return productViewRepository.findByProductInfoSeq(productDetailSeq);
//    }
//
//    @Override
//    public ProductInfo selectProductDetail(Long productInfoSeq) {
//        return productDslRepository.selectProductDetail(productInfoSeq);
//    }
//
//    @Override
//    public ProductDto selectProduct(Long productSeq) {
//        return productDslRepository.selectProduct(productSeq);
//    }
//
//    @Override
//    public Page<ProductView> productViewFindAll(Pageable pageable) {
//        return productViewRepository.findAll(pageable);
//    }
//
//    @Override
//    public Page<AdminProductListDto> selectProductList(Pageable pageable) {
//        return productDslRepository.selectProductList(pageable);
//    }
}
