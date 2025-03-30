package com.commerce.core.common.type;

import lombok.Builder;

import java.util.List;

@Builder
public record PageListResponse<T>(
        int totalPage,
        List<T> list
) {

}
