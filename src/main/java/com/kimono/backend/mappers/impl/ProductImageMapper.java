package com.kimono.backend.mappers.impl;

import com.kimono.backend.domain.dto.ProductImageDto;
import com.kimono.backend.domain.entities.ProductImageEntity;
import com.kimono.backend.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ProductImageMapper implements Mapper<ProductImageEntity, ProductImageDto> {
    private final ModelMapper modelMapper;

    public ProductImageMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public ProductImageDto mapTo(ProductImageEntity entity) {
        return modelMapper.map(entity, ProductImageDto.class);
    }

    @Override
    public ProductImageEntity mapFrom(ProductImageDto dto) {
        return modelMapper.map(dto, ProductImageEntity.class);
    }

}
