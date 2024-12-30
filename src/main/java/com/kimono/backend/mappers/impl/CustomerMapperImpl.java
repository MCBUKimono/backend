package com.kimono.backend.mappers.impl;

import com.kimono.backend.domain.dto.CustomerDto;
import com.kimono.backend.domain.entities.CustomerEntity;
import com.kimono.backend.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapperImpl implements Mapper<CustomerEntity, CustomerDto> {
    private final ModelMapper modelMapper;

    public CustomerMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public CustomerDto mapTo(CustomerEntity entity) {
        return modelMapper.map(entity, CustomerDto.class);
    }

    @Override
    public CustomerEntity mapFrom(CustomerDto dto) {
        return modelMapper.map(dto, CustomerEntity.class);
    }
}
