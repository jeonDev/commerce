package com.commerce.core.comn.entity;

import com.commerce.core.common.entity.BaseEntity;
import com.commerce.core.comn.vo.FileDivisionCode;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Table(name = "COMMON_FILE")
public class CommonFile extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMMON_FILE_SEQ")
    private Long commonFileSeq;

    @Column(name = "FILE_DIVISION_CODE")
    @Enumerated(EnumType.STRING)
    private FileDivisionCode fileDivisionCode;

    @Column(name = "FILE_REF_SEQ")
    private Long fileRefSeq;

    @Column(name = "FILE_PATH", nullable = false)
    private String filePath;

    @Column(name = "USE_YN")
    private String useYn;

    @Column(name = "ORDER_NUMBER")
    private Integer orderNumber;
}
