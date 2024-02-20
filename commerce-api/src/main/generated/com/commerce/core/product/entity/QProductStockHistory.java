package com.commerce.core.product.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductStockHistory is a Querydsl query type for ProductStockHistory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductStockHistory extends EntityPathBase<ProductStockHistory> {

    private static final long serialVersionUID = -227012022L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProductStockHistory productStockHistory = new QProductStockHistory("productStockHistory");

    public final com.commerce.core.common.entity.QBaseEntity _super = new com.commerce.core.common.entity.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDt = _super.createDt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDt = _super.lastModifiedDt;

    public final QProduct product;

    public final NumberPath<Long> productHistorySeq = createNumber("productHistorySeq", Long.class);

    public final EnumPath<com.commerce.core.product.vo.ProductStockProcessStatus> productStockProcessStatus = createEnum("productStockProcessStatus", com.commerce.core.product.vo.ProductStockProcessStatus.class);

    public final NumberPath<Long> stock = createNumber("stock", Long.class);

    public QProductStockHistory(String variable) {
        this(ProductStockHistory.class, forVariable(variable), INITS);
    }

    public QProductStockHistory(Path<? extends ProductStockHistory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProductStockHistory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProductStockHistory(PathMetadata metadata, PathInits inits) {
        this(ProductStockHistory.class, metadata, inits);
    }

    public QProductStockHistory(Class<? extends ProductStockHistory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.product = inits.isInitialized("product") ? new QProduct(forProperty("product"), inits.get("product")) : null;
    }

}

