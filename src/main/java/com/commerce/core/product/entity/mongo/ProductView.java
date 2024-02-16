package com.commerce.core.product.entity.mongo;

import com.commerce.core.product.vo.ProductStockSummary;
import com.commerce.core.product.vo.ProductViewResDto;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 상품 정보 View
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "PRODUCT_VIEW")
public class ProductView {

    @Id
    private String id;

    /**
     * 상품 정보 Seq
     */
    private Long productInfoSeq;

    /**
     * 상품명
     */
    private String productName;

    /**
     * 상품 상세 설명
     */
    private String productDetail;

    /**
     * 상품 가격
     */
    private Long price;

    /**
     * 상품 할인 가격 (실제 판매 가격)
     */
    private Long discountPrice;

    /**
     * 사용 여부 (Y / N)
     */
    private String useYn;

    /**
     * 상품 옵션
     */
    private String productOption;

    /**
     * 재고 현황
     */
    private ProductStockSummary productStockSummary;

    public ProductView productViewSyncUpdate(Long productInfoSeq, String productName, String productDetail, Long price, String useYn, String productOption, ProductStockSummary productStockSummary) {
        this.productInfoSeq = productInfoSeq;
        this.productName = productName;
        this.productDetail = productDetail;
        this.price = price;
        this.useYn = useYn;
        this.productOption = productOption;
        this.productStockSummary = productStockSummary;

        return this;
    }

    public ProductViewResDto documentToResDto() {
        return ProductViewResDto.builder()
                .productInfoSeq(productInfoSeq)
                .productName(productName)
                .productDetail(productDetail)
                .price(price)
                .discountPrice(discountPrice)
                .useYn(useYn)
                .productOption(productOption)
                .build();
    }
}
