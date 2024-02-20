package com.commerce.core.point.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPointHistory is a Querydsl query type for PointHistory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPointHistory extends EntityPathBase<PointHistory> {

    private static final long serialVersionUID = -1257853880L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPointHistory pointHistory = new QPointHistory("pointHistory");

    public final com.commerce.core.common.entity.QBaseEntity _super = new com.commerce.core.common.entity.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDt = _super.createDt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDt = _super.lastModifiedDt;

    public final com.commerce.core.member.entity.QMember member;

    public final NumberPath<Long> point = createNumber("point", Long.class);

    public final NumberPath<Long> pointHistorySeq = createNumber("pointHistorySeq", Long.class);

    public final EnumPath<com.commerce.core.point.vo.PointProcessStatus> pointProcessStatus = createEnum("pointProcessStatus", com.commerce.core.point.vo.PointProcessStatus.class);

    public QPointHistory(String variable) {
        this(PointHistory.class, forVariable(variable), INITS);
    }

    public QPointHistory(Path<? extends PointHistory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPointHistory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPointHistory(PathMetadata metadata, PathInits inits) {
        this(PointHistory.class, metadata, inits);
    }

    public QPointHistory(Class<? extends PointHistory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.commerce.core.member.entity.QMember(forProperty("member")) : null;
    }

}

