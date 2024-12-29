package com.kimono.backend.controllers;

import com.kimono.backend.domain.dto.CategoryDto;
import com.kimono.backend.domain.entities.CategoryEntity;
import com.kimono.backend.mappers.Mapper;
import com.kimono.backend.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CategoryController {

    private final CategoryService categoryService;
    private final Mapper<CategoryEntity, CategoryDto> mapper;

    @Autowired
    public CategoryController(CategoryService categoryService, Mapper<CategoryEntity, CategoryDto> mapper) {
        this.categoryService = categoryService;
        this.mapper = mapper;
    }

    @PostMapping(path = "/category")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto) {
        CategoryEntity categoryEntity = mapper.mapFrom(categoryDto);
        CategoryEntity savedCategoryEntity = categoryService.save(categoryEntity);
        return new ResponseEntity<>(mapper.mapTo(savedCategoryEntity), HttpStatus.CREATED);
    }

    @GetMapping(path = "/category")
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<CategoryEntity> categoryEntities = categoryService.findAll();
        List<CategoryDto> categoryDtos = categoryEntities.stream().map(mapper::mapTo).collect(Collectors.toList());
        return new ResponseEntity<>(categoryDtos, HttpStatus.OK);
    }

    @GetMapping(path = "/category/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Integer id) {
        CategoryEntity categoryEntity = categoryService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
        return new ResponseEntity<>(mapper.mapTo(categoryEntity), HttpStatus.OK);
    }

    @PutMapping(path = "/category/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable Integer id, @RequestBody CategoryDto categoryDto) {
        if (categoryService.doesNotExist(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found");
        }

        categoryDto.setId(id);
        CategoryEntity categoryEntity = mapper.mapFrom(categoryDto);
        CategoryEntity updatedCategoryEntity = categoryService.save(categoryEntity);
        return new ResponseEntity<>(mapper.mapTo(updatedCategoryEntity), HttpStatus.OK);
    }

    @PatchMapping(path = "/category/{id}")
    public ResponseEntity<CategoryDto> updateCategoryPartial(@PathVariable Integer id, @RequestBody CategoryDto categoryDto) {
        if (categoryService.doesNotExist(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found");
        }

        CategoryEntity categoryEntity = mapper.mapFrom(categoryDto);
        CategoryEntity updatedCategoryEntity = categoryService.partialUpdate(id, categoryEntity);
        return new ResponseEntity<>(mapper.mapTo(updatedCategoryEntity), HttpStatus.OK);
    }

    @DeleteMapping(path = "/category/{id}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable Integer id) {
        try {
            categoryService.deleteCategoryById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
