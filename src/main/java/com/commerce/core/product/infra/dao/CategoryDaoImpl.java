package com.commerce.core.product.infra.dao;

import com.commerce.core.product.domain.CategoryDao;
import com.commerce.core.product.domain.entity.Category;
import com.commerce.core.product.infra.dao.jpa.CategoryJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CategoryDaoImpl implements CategoryDao {

    private final CategoryJpaRepository cetegoryJpaRepository;

    public CategoryDaoImpl(CategoryJpaRepository cetegoryJpaRepository) {
        this.cetegoryJpaRepository = cetegoryJpaRepository;
    }

    @Override
    public Category save(Category category) {
        return cetegoryJpaRepository.save(category);
    }

    @Override
    public Optional<Category> findById(Long categorySeq) {
        return cetegoryJpaRepository.findById(categorySeq);
    }
}
