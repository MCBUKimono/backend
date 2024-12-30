package com.kimono.backend.mappers.impl;

import com.kimono.backend.domain.dto.ProductSnapshotDto;
import com.kimono.backend.domain.entities.ProductSnapshotEntity;
import com.kimono.backend.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ProductSnapshotMapperImpl implements Mapper<ProductSnapshotEntity, ProductSnapshotDto> {
    private final ModelMapper modelMapper;

    public ProductSnapshotMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public ProductSnapshotDto mapTo(ProductSnapshotEntity entity) {
        return modelMapper.map(entity, ProductSnapshotDto.class);
    }

    @Override
    public ProductSnapshotEntity mapFrom(ProductSnapshotDto dto) {
        return modelMapper.map(dto, ProductSnapshotEntity.class);
    }
}
