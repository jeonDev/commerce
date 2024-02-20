package com.commerce.core.product.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QProductInfo is a Querydsl query type for ProductInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductInfo extends EntityPathBase<ProductInfo> {

    private static final long serialVersionUID = 1420321370L;

    public static final QProductInfo productInfo = new QProductInfo("productInfo");

    public final com.commerce.core.common.entity.QBaseEntity _super = new com.commerce.core.common.entity.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDt = _super.createDt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDt = _super.lastModifiedDt;

    public final NumberPath<Long> price = createNumber("price", Long.class);

    public final StringPath productDetail = createString("productDetail");

    public final NumberPath<Long> productInfoSeq = createNumber("productInfoSeq", Long.class);

    public final StringPath productName = createString("productName");

    public QProductInfo(String variable) {
        super(ProductInfo.class, forVariable(variable));
    }

    public QProductInfo(Path<? extends ProductInfo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProductInfo(PathMetadata metadata) {
        super(ProductInfo.class, metadata);
    }

}

