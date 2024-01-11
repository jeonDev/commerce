package com.commerce.core.product.vo;

import com.commerce.core.product.entity.Product;
import com.commerce.core.common.vo.PageDto;
import com.commerce.core.product.entity.ProductDetail;
import com.commerce.core.product.entity.ProductInfo;
import lombok.Data;

@Data
public class ProductDto extends PageDto {
    private Long productSeq;
    private String productName;
    private String productOptionCode;
    private ProductInfoDto productInfoDto;
    private ProductDetailDto productDetailDto;

    /**
     * Dto -> Entity
     * @param productInfo
     * @param productDetail
     * @return
     */
    public Product dtoToEntity(ProductInfo productInfo, ProductDetail productDetail) {
        return Product.builder()
                .productSeq(productSeq)
                .productInfo(productInfo)
                .productDetail(productDetail)
                .productOptionCode(productOptionCode)
                .build();
    }
}
