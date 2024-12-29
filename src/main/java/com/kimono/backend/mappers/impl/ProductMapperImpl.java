package com.kimono.backend.mappers.impl;

import com.kimono.backend.domain.dto.ProductDto;
import com.kimono.backend.domain.entities.CategoryEntity;
import com.kimono.backend.domain.entities.ProductEntity;
import com.kimono.backend.mappers.Mapper;
import com.kimono.backend.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ProductMapperImpl implements Mapper<ProductEntity, ProductDto> {

    private final ModelMapper modelMapper;
    private final CategoryService categoryService;

    public ProductMapperImpl(ModelMapper modelMapper, CategoryService categoryService) {
        this.modelMapper = modelMapper;
        this.categoryService = categoryService;
    }

    @Override
    public ProductDto mapTo(ProductEntity entity) {
        return modelMapper.map(entity, ProductDto.class);
    }

    @Override
    public ProductEntity mapFrom(ProductDto dto) {
        CategoryEntity category= null;
        if(dto.getCategory() != null) {
            if(dto.getCategory().getId() != null) {
                category = categoryService.findById(dto.getCategory().getId())
                        .orElseThrow(() -> new IllegalArgumentException("Category not found"));
            }
            else if (dto.getCategory().getName() != null) {
                category = categoryService.findByName(dto.getCategory().getName())
                        .orElseThrow(() -> new IllegalArgumentException("Category not found"));
            }
            else {
                throw new IllegalArgumentException("Category not found");
            }
        }

        ProductEntity productEntity = modelMapper.map(dto, ProductEntity.class);
        productEntity.setCategory(category);
        return productEntity;
    }
}
