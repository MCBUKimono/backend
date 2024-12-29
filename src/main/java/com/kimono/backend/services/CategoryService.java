package com.kimono.backend.services;

import com.kimono.backend.domain.entities.CategoryEntity;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    CategoryEntity save(CategoryEntity categoryEntity);

    Optional<CategoryEntity> findById(Integer id);

    List<CategoryEntity> findAll();

    Optional<CategoryEntity> findByName(String name);

    void deleteCategoryById(Integer id);

    boolean doesNotExist(Integer id);

    CategoryEntity partialUpdate(Integer id, CategoryEntity categoryEntity);

}
