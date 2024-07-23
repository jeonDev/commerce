package com.commerce.core.common.vo;

import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class PageListVO<T> {
    private int totalPage;
    private List<T> list;
}
