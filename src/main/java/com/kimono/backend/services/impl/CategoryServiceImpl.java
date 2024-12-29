package com.kimono.backend.services.impl;

import com.kimono.backend.domain.entities.CategoryEntity;
import com.kimono.backend.repositories.CategoryRepository;
import com.kimono.backend.services.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryEntity save(CategoryEntity categoryEntity) {
        return categoryRepository.save(categoryEntity);
    }

    @Override
    public Optional<CategoryEntity> findById(Integer id) {
        return categoryRepository.findById(id);
    }

    @Override
    public List<CategoryEntity> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Optional<CategoryEntity> findByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public void deleteCategoryById(Integer id) {
        if(doesNotExist(id)){
            throw new EntityNotFoundException();
        }
        categoryRepository.deleteById(id);
    }

    @Override
    public boolean doesNotExist(Integer id) {
        return !categoryRepository.existsById(id);
    }

    @Override
    public CategoryEntity partialUpdate(Integer id, CategoryEntity categoryEntity) {
        return categoryRepository.findById(id).map(existingCategory -> {
            Optional.ofNullable(categoryEntity.getName()).ifPresent(existingCategory::setName);
            Optional.ofNullable(categoryEntity.getParentCategory()).ifPresent(existingCategory::setParentCategory);
            Optional.ofNullable(categoryEntity.getSubCategories()).ifPresent(existingCategory::setSubCategories);
            Optional.ofNullable(categoryEntity.getProducts()).ifPresent(existingCategory::setProducts);
            return categoryRepository.save(existingCategory);
        }).orElseThrow(() -> new RuntimeException("Category does not exist"));
    }
}
