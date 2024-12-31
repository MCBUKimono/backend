package com.kimono.backend.mappers.impl;

import com.kimono.backend.domain.dto.CategoryDto;
import com.kimono.backend.domain.entities.CategoryEntity;
import com.kimono.backend.domain.entities.ProductEntity;
import com.kimono.backend.mappers.Mapper;
import com.kimono.backend.repositories.CategoryRepository;
import com.kimono.backend.repositories.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryMapperImpl implements Mapper<CategoryEntity, CategoryDto> {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public CategoryMapperImpl(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    public CategoryDto mapTo(CategoryEntity entity) {
        if (entity == null) {
            return null;
        }

        return CategoryDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .parentCategoryId(entity.getParentCategory() != null ? entity.getParentCategory().getId() : null)
                .subCategoryIds(entity.getSubCategories() != null ?
                        entity.getSubCategories().stream()
                                .map(CategoryEntity::getId)
                                .collect(Collectors.toList()) : null)
                .productIds(entity.getProducts() != null ?
                        entity.getProducts().stream()
                                .map(ProductEntity::getId)
                                .collect(Collectors.toList()) : null)
                .build();
    }

    @Override
    public CategoryEntity mapFrom(CategoryDto dto) {
        if (dto == null) {
            return null;
        }

        // Parent Category
        CategoryEntity parentCategory = null;
        if (dto.getParentCategoryId() != null) {
            parentCategory = categoryRepository.findById(dto.getParentCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("Parent category not found with id: " + dto.getParentCategoryId()));
        }

        // Products
        List<ProductEntity> products = null;
        if (dto.getProductIds() != null) {
            products = dto.getProductIds().stream()
                    .map(productId -> productRepository.findById(productId)
                            .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + productId)))
                    .collect(Collectors.toList());
        }

        return CategoryEntity.builder()
                .id(dto.getId())
                .name(dto.getName())
                .parentCategory(parentCategory)
                .products(products)
                .build();
    }
}
