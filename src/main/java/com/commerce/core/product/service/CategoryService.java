package com.commerce.core.product.service;

import com.commerce.core.product.domain.CategoryDao;
import com.commerce.core.product.domain.entity.Category;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryService {

    private final CategoryDao categoryDao;

    public CategoryService(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @Transactional
    public Category merge(Category category) {
        return categoryDao.save(category);
    }
}
