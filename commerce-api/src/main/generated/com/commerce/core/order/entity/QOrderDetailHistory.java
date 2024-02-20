package com.commerce.core.order.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrderDetailHistory is a Querydsl query type for OrderDetailHistory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrderDetailHistory extends EntityPathBase<OrderDetailHistory> {

    private static final long serialVersionUID = 1110194935L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrderDetailHistory orderDetailHistory = new QOrderDetailHistory("orderDetailHistory");

    public final com.commerce.core.common.entity.QBaseEntity _super = new com.commerce.core.common.entity.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDt = _super.createDt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDt = _super.lastModifiedDt;

    public final QOrderDetail orderDetail;

    public final NumberPath<Long> orderDetailHistorySeq = createNumber("orderDetailHistorySeq", Long.class);

    public final QOrders orders;

    public final EnumPath<com.commerce.core.order.vo.OrderStatus> orderStatus = createEnum("orderStatus", com.commerce.core.order.vo.OrderStatus.class);

    public final com.commerce.core.product.entity.QProduct product;

    public QOrderDetailHistory(String variable) {
        this(OrderDetailHistory.class, forVariable(variable), INITS);
    }

    public QOrderDetailHistory(Path<? extends OrderDetailHistory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrderDetailHistory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrderDetailHistory(PathMetadata metadata, PathInits inits) {
        this(OrderDetailHistory.class, metadata, inits);
    }

    public QOrderDetailHistory(Class<? extends OrderDetailHistory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.orderDetail = inits.isInitialized("orderDetail") ? new QOrderDetail(forProperty("orderDetail"), inits.get("orderDetail")) : null;
        this.orders = inits.isInitialized("orders") ? new QOrders(forProperty("orders"), inits.get("orders")) : null;
        this.product = inits.isInitialized("product") ? new com.commerce.core.product.entity.QProduct(forProperty("product"), inits.get("product")) : null;
    }

}

