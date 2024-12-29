package com.kimono.backend.mappers.impl;

import com.kimono.backend.domain.dto.BrandDto;
import com.kimono.backend.domain.entities.BrandEntity;
import com.kimono.backend.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class BrandMapperImpl implements Mapper<BrandEntity, BrandDto> {

    private final ModelMapper modelMapper;


    public BrandMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public BrandDto mapTo(BrandEntity brandEntity) {
        return modelMapper.map(brandEntity, BrandDto.class);
    }

    @Override
    public BrandEntity mapFrom(BrandDto brandDto) {
        return modelMapper.map(brandDto, BrandEntity.class);
    }
}
