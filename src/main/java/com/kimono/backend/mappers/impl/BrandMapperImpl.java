package com.kimono.backend.mappers.impl;

import com.kimono.backend.domain.dto.BrandDto;
import com.kimono.backend.domain.entities.BrandEntity;
import com.kimono.backend.mappers.Mapper;
import org.springframework.stereotype.Component;

@Component
public class BrandMapperImpl implements Mapper<BrandEntity, BrandDto> {

    @Override
    public BrandDto mapTo(BrandEntity entity) {
        if (entity == null) {
            return null;
        }

        return BrandDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

    @Override
    public BrandEntity mapFrom(BrandDto dto) {
        if (dto == null) {
            return null;
        }

        return BrandEntity.builder()
                .id(dto.getId())
                .name(dto.getName())
                .build();
    }
}
