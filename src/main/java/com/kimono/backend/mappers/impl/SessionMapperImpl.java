package com.kimono.backend.mappers.impl;

import com.kimono.backend.domain.dto.SessionDto;
import com.kimono.backend.domain.entities.SessionEntity;
import com.kimono.backend.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class SessionMapperImpl implements Mapper<SessionEntity, SessionDto> {
    private final ModelMapper modelMapper;

    public SessionMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public SessionDto mapTo(SessionEntity entity) {
        return modelMapper.map(entity, SessionDto.class);
    }

    @Override
    public SessionEntity mapFrom(SessionDto dto) {
        return modelMapper.map(dto, SessionEntity.class);
    }
}
