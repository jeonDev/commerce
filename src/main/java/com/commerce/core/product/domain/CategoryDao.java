package com.commerce.core.product.domain;

import com.commerce.core.product.domain.entity.Category;

import java.util.Optional;

public interface CategoryDao {
    Category save(Category category);
    Optional<Category> findById(Long categorySeq);
}
