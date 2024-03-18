package com.commerce.core.product.repository.dsl;

import com.commerce.core.product.entity.Product;
import com.commerce.core.product.vo.ProductDetailDto;
import com.commerce.core.product.vo.ProductDto;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.commerce.core.product.entity.QProduct.product;

@RequiredArgsConstructor
@Repository
public class ProductDslRepository {

    private final JPAQueryFactory dsl;

    public ProductDetailDto selectProductDetail(Long productViewSeq) {
        return null;
    }

//    public Page<Product> findByAll(ProductDto dto) {
//        List<Product> content = dsl
//                .selectFrom(product)
//                .offset(dto.getPage().getOffset())
//                .limit(dto.getPage().getPageSize())
//                .fetch();
//
//        JPAQuery<Long> countQuery = dsl
//                .select(product.count())
//                .from(product);
//
//        return PageableExecutionUtils.getPage(content, dto.getPage(), countQuery::fetchCount);
//    }
}
