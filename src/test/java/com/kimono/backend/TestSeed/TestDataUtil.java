package com.kimono.backend.TestSeed;

import com.kimono.backend.domain.dto.*;
import com.kimono.backend.domain.entities.*;

public final class TestDataUtil {
    private TestDataUtil(){
    }

    public static BrandEntity createTestBrandA(){
        return BrandEntity.builder()
                .name("Samsung")
                .build();
    }

    public static BrandDto createTestBrandDtoA() {
        return BrandDto.builder()
                .name("Samsung")
                .build();
    }

}