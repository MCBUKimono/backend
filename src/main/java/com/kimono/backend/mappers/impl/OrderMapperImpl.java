package com.kimono.backend.mappers.impl;

import com.kimono.backend.domain.dto.OrderDto;
import com.kimono.backend.domain.entities.OrderEntity;
import com.kimono.backend.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class OrderMapperImpl implements Mapper<OrderEntity, OrderDto> {
    private final ModelMapper modelMapper;

    public OrderMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public OrderDto mapTo(OrderEntity entity) {
        return modelMapper.map(entity, OrderDto.class);
    }

    @Override
    public OrderEntity mapFrom(OrderDto dto) {
        return modelMapper.map(dto, OrderEntity.class);
    }
}
