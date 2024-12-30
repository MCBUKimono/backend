package com.kimono.backend.mappers.impl;

import com.kimono.backend.domain.dto.ProductDto;
import com.kimono.backend.domain.entities.ProductEntity;
import com.kimono.backend.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ProductMapperImpl implements Mapper<ProductEntity, ProductDto> {
    private final ModelMapper modelMapper;

    public ProductMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public ProductDto mapTo(ProductEntity entity) {
        return modelMapper.map(entity, ProductDto.class);
    }

    @Override
    public ProductEntity mapFrom(ProductDto dto) {
        return modelMapper.map(dto, ProductEntity.class);
    }
}
