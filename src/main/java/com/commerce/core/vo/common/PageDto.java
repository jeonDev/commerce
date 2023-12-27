package com.commerce.core.vo.common;

import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Data
public class PageDto {
    private int pageNo;
    private int pageSize;

    public Pageable getPage(Sort.Direction sort, String... sortColumn) {
        return PageRequest.of(pageNo, pageSize, sort, sortColumn);
    }

    public Pageable getPage() {
        return PageRequest.of(pageNo, pageSize);
    }
}
