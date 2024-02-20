package com.commerce.core.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = 242283514L;

    public static final QMember member = new QMember("member1");

    public final com.commerce.core.common.entity.QBaseEntity _super = new com.commerce.core.common.entity.QBaseEntity(this);

    public final StringPath addr = createString("addr");

    public final StringPath addrDetail = createString("addrDetail");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDt = _super.createDt;

    public final StringPath id = createString("id");

    public final StringPath lastLoginDttm = createString("lastLoginDttm");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDt = _super.lastModifiedDt;

    public final NumberPath<Long> memberSeq = createNumber("memberSeq", Long.class);

    public final StringPath name = createString("name");

    public final StringPath password = createString("password");

    public final NumberPath<Long> passwordFailCount = createNumber("passwordFailCount", Long.class);

    public final StringPath tel = createString("tel");

    public final StringPath useYn = createString("useYn");

    public final StringPath zipCode = createString("zipCode");

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

