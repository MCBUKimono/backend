package com.kimono.backend.mapper;

import com.kimono.backend.domain.dto.BrandDto;
import com.kimono.backend.domain.entities.BrandEntity;
import com.kimono.backend.mappers.impl.BrandMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BrandMapperImplTest {

    private BrandMapperImpl brandMapper;

    @BeforeEach
    void setUp() {
        brandMapper = new BrandMapperImpl();
    }

    @Test
    void testMapToBrandDto() {
        BrandEntity brandEntity = BrandEntity.builder()
                .id(1)
                .name("Samsung")
                .build();

        BrandDto brandDto = brandMapper.mapTo(brandEntity);

        assertNotNull(brandDto);
        assertEquals(brandEntity.getId(), brandDto.getId());
        assertEquals(brandEntity.getName(), brandDto.getName());
    }

    @Test
    void testMapToBrandDtoWithNull() {
        BrandDto brandDto = brandMapper.mapTo(null);

        assertNull(brandDto);
    }

    @Test
    void testMapFromBrandDto() {
        // Given
        BrandDto brandDto = BrandDto.builder()
                .id(1)
                .name("Apple")
                .build();

        // When
        BrandEntity brandEntity = brandMapper.mapFrom(brandDto);

        // Then
        assertNotNull(brandEntity);
        assertEquals(brandDto.getId(), brandEntity.getId());
        assertEquals(brandDto.getName(), brandEntity.getName());
    }

    @Test
    void testMapFromBrandDtoWithNull() {
        // When
        BrandEntity brandEntity = brandMapper.mapFrom(null);

        // Then
        assertNull(brandEntity);
    }
}
