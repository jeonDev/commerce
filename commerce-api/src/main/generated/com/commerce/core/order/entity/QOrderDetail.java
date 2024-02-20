package com.commerce.core.order.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrderDetail is a Querydsl query type for OrderDetail
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrderDetail extends EntityPathBase<OrderDetail> {

    private static final long serialVersionUID = 1791901405L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrderDetail orderDetail = new QOrderDetail("orderDetail");

    public final com.commerce.core.common.entity.QBaseEntity _super = new com.commerce.core.common.entity.QBaseEntity(this);

    public final NumberPath<Long> amount = createNumber("amount", Long.class);

    public final NumberPath<Long> buyAmount = createNumber("buyAmount", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDt = _super.createDt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDt = _super.lastModifiedDt;

    public final NumberPath<Long> orderDetailSeq = createNumber("orderDetailSeq", Long.class);

    public final QOrders orders;

    public final EnumPath<com.commerce.core.order.vo.OrderStatus> orderStatus = createEnum("orderStatus", com.commerce.core.order.vo.OrderStatus.class);

    public final NumberPath<Long> paidAmount = createNumber("paidAmount", Long.class);

    public final com.commerce.core.product.entity.QProduct product;

    public QOrderDetail(String variable) {
        this(OrderDetail.class, forVariable(variable), INITS);
    }

    public QOrderDetail(Path<? extends OrderDetail> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrderDetail(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrderDetail(PathMetadata metadata, PathInits inits) {
        this(OrderDetail.class, metadata, inits);
    }

    public QOrderDetail(Class<? extends OrderDetail> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.orders = inits.isInitialized("orders") ? new QOrders(forProperty("orders"), inits.get("orders")) : null;
        this.product = inits.isInitialized("product") ? new com.commerce.core.product.entity.QProduct(forProperty("product"), inits.get("product")) : null;
    }

}

