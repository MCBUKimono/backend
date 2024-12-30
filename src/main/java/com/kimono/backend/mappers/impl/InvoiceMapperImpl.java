package com.kimono.backend.mappers.impl;

import com.kimono.backend.domain.dto.InvoiceDto;
import com.kimono.backend.domain.entities.InvoiceEntity;
import com.kimono.backend.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class InvoiceMapperImpl implements Mapper<InvoiceEntity, InvoiceDto> {
    private final ModelMapper modelMapper;

    public InvoiceMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public InvoiceDto mapTo(InvoiceEntity entity) {
        return modelMapper.map(entity, InvoiceDto.class);
    }

    @Override
    public InvoiceEntity mapFrom(InvoiceDto dto) {
        return modelMapper.map(dto, InvoiceEntity.class);
    }
}
