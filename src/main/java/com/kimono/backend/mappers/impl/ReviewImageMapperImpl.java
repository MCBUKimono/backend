package com.kimono.backend.mappers.impl;

import com.kimono.backend.domain.dto.ReviewImageDto;
import com.kimono.backend.domain.entities.ReviewImageEntity;
import com.kimono.backend.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ReviewImageMapperImpl implements Mapper<ReviewImageEntity, ReviewImageDto> {
    private final ModelMapper modelMapper;

    public ReviewImageMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public ReviewImageDto mapTo(ReviewImageEntity entity) {
        return modelMapper.map(entity, ReviewImageDto.class);
    }

    @Override
    public ReviewImageEntity mapFrom(ReviewImageDto dto) {
        return modelMapper.map(dto, ReviewImageEntity.class);
    }
}
