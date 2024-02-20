package com.commerce.core.order.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPaymentHistory is a Querydsl query type for PaymentHistory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPaymentHistory extends EntityPathBase<PaymentHistory> {

    private static final long serialVersionUID = 1665131856L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPaymentHistory paymentHistory = new QPaymentHistory("paymentHistory");

    public final com.commerce.core.common.entity.QBaseEntity _super = new com.commerce.core.common.entity.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDt = _super.createDt;

    public final EnumPath<com.commerce.core.order.vo.InoutDivisionStatus> inoutDivisionStatus = createEnum("inoutDivisionStatus", com.commerce.core.order.vo.InoutDivisionStatus.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDt = _super.lastModifiedDt;

    public final QOrderDetail orderDetail;

    public final NumberPath<Long> paymentHistorySeq = createNumber("paymentHistorySeq", Long.class);

    public final NumberPath<Long> point = createNumber("point", Long.class);

    public QPaymentHistory(String variable) {
        this(PaymentHistory.class, forVariable(variable), INITS);
    }

    public QPaymentHistory(Path<? extends PaymentHistory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPaymentHistory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPaymentHistory(PathMetadata metadata, PathInits inits) {
        this(PaymentHistory.class, metadata, inits);
    }

    public QPaymentHistory(Class<? extends PaymentHistory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.orderDetail = inits.isInitialized("orderDetail") ? new QOrderDetail(forProperty("orderDetail"), inits.get("orderDetail")) : null;
    }

}

