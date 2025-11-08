package com.commerce.core.product.infra.dao.querydsl;

import com.commerce.core.product.domain.dto.ProductDto;
import com.commerce.core.product.domain.dto.AdminProductListDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.commerce.core.product.domain.entity.QProduct.product;

@RequiredArgsConstructor
@Repository
public class ProductDslRepository {
//
//    private final JPAQueryFactory dsl;
//
//    public ProductInfo selectProductDetail(Long productInfoSeq) {
//        return dsl.selectFrom(productInfo)
//                .where(productInfo.productInfoSeq.eq(productInfoSeq))
//                .fetchOne();
//    }
//
//    public ProductDto selectProduct(Long productSeq) {
//        return dsl.select(Projections.bean(ProductDto.class,
//                        product.productSeq,
//                        product.productOptionCode,
//                        productInfo.productInfoSeq,
//                        productInfo.productName,
//                        productInfo.productDetail,
//                        productInfo.price
//                        )
//                )
//                .from(product)
//                .join(product.productInfo, productInfo)
//                .where(product.productSeq.eq(productSeq))
//                .fetchOne();
//    }
//
//    public Page<AdminProductListDto> selectProductList(Pageable pageable) {
//        List<AdminProductListDto> content = dsl.select(Projections.bean(AdminProductListDto.class,
//                        productInfo.productInfoSeq,
//                        productInfo.productName,
//                        productInfo.productDetail,
//                        productInfo.price.coalesce(0L).as("price")
//                        )
//                )
//                .from(productInfo)
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
//                .fetch();
//
//        Long totalCount = dsl.select(productInfo.count())
//                .from(productInfo)
//                .fetchOne();
//
//        return new PageImpl<>(content, pageable, totalCount);
//    }
}
