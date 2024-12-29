package com.kimono.backend.mappers.impl;

import com.kimono.backend.domain.dto.CategoryDto;
import com.kimono.backend.domain.entities.CategoryEntity;
import com.kimono.backend.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapperImpl implements Mapper<CategoryEntity, CategoryDto> {

    private final ModelMapper modelMapper;

    public CategoryMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryDto mapTo(CategoryEntity entity) {
        return modelMapper.map(entity, CategoryDto.class);
    }

    @Override
    public CategoryEntity mapFrom(CategoryDto dto) {
        return modelMapper.map(dto, CategoryEntity.class);
    }
}
