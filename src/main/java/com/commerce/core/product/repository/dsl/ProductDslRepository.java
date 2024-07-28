package com.commerce.core.product.repository.dsl;

import com.commerce.core.product.entity.ProductInfo;
import com.commerce.core.product.repository.dsl.vo.ProductDAO;
import com.commerce.core.product.vo.AdminProductListResDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.commerce.core.product.entity.QProduct.product;
import static com.commerce.core.product.entity.QProductInfo.productInfo;

@RequiredArgsConstructor
@Repository
public class ProductDslRepository {

    private final JPAQueryFactory dsl;

    public ProductInfo selectProductDetail(Long productInfoSeq) {
        return dsl.selectFrom(productInfo)
                .where(productInfo.productInfoSeq.eq(productInfoSeq))
                .fetchOne();
    }

    public ProductDAO selectProduct(Long productSeq) {
        return dsl.select(Projections.bean(ProductDAO.class,
                        product.productSeq,
                        product.productOptionCode,
                        productInfo.productInfoSeq,
                        productInfo.productName,
                        productInfo.productDetail,
                        productInfo.price
                        )
                )
                .from(product)
                .join(product.productInfo, productInfo)
                .where(product.productSeq.eq(productSeq))
                .fetchOne();
    }

    public Page<AdminProductListResDto> selectProductList(Pageable pageable) {
        List<AdminProductListResDto> content = dsl.select(Projections.bean(AdminProductListResDto.class,
                        product.productSeq
                        )
                )
                .from(product)
                .join(product.productInfo, productInfo)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long totalCount = dsl.select(productInfo.count())
                .from(product)
                .join(product.productInfo, productInfo)
                .fetchOne();

        return new PageImpl<>(content, pageable, totalCount);
    }
}
