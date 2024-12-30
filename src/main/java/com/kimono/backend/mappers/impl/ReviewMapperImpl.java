package com.kimono.backend.mappers.impl;

import com.kimono.backend.domain.dto.ReviewDto;
import com.kimono.backend.domain.entities.ReviewEntity;
import com.kimono.backend.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapperImpl implements Mapper<ReviewEntity, ReviewDto> {
    private final ModelMapper modelMapper;

    public ReviewMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public ReviewDto mapTo(ReviewEntity entity) {
        return modelMapper.map(entity, ReviewDto.class);
    }

    @Override
    public ReviewEntity mapFrom(ReviewDto dto) {
        return modelMapper.map(dto, ReviewEntity.class);
    }
}
