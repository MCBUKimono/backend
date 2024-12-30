package com.kimono.backend.TestSeed;

import com.kimono.backend.domain.dto.*;
import com.kimono.backend.domain.entities.*;

import java.util.List;

public final class TestDataUtil {
    private TestDataUtil(){
    }

    public static BrandEntity createTestBrandA(){
        return BrandEntity.builder()
                .name("Samsung")
                .build();
    }

    public static BrandEntity createTestBrandB(){
        return BrandEntity.builder()
                .name("Apple")
                .build();
    }

    public static BrandDto createTestBrandDtoA() {
        return BrandDto.builder()
                .name("Samsung")
                .build();
    }

    public static ProductImageEntity createTestProductImageEntityA(){
        return ProductImageEntity.builder()
                .product(null)
                .displayOrder(123123)
                .build();
    }
    public static ProductImageEntity createTestProductImageEntityB(){
        return ProductImageEntity.builder()
                .product(null)
                .displayOrder(123123)
                .build();
    }

    public static ProductImageDto createTestProductImageDtoA(){
        return ProductImageDto.builder()
                .productId(null)
                .displayOrder(123123)
                .build();
    }

    public static ProductImageDto createTestProductImageDtoB(){
        return ProductImageDto.builder()
                .productId(null)
                .displayOrder(123123)
                .build();
    }

    public static CategoryEntity createTestCategoryEntityA() {
        return CategoryEntity.builder()
                .name("Telefon")
                .build();
    }
    public static CategoryEntity createTestCategoryEntityB() {
        return CategoryEntity.builder()
                .name("Bilgisayar")
                .build();
    }
    public static CategoryEntity createTestCategoryEntityC() {
        return CategoryEntity.builder()
                .name("Yardımcı araçlar")
                .build();
    }

    public static CategoryDto createTestCategoryDtoA() {
        return CategoryDto.builder()
                .name("Elektdronik")
                .build();
    }

    public static ProductEntity createTestProductA(){
        return ProductEntity.builder()
                .name("Telefon")
                .description("Akıllı Telefon")
//                .brand(createTestBrandA())
//                .images(List.of(createTestProductImageEntityA(),createTestProductImageEntityB()))
//                .category(createTestCategoryEntityA())
                .averageScoreOutOf100(11.2)
                .priceCents(10000)
                .taxPer1000(10)
                .discountPer1000(100)
                .build();
    }

    public static ProductDto createTestProductDtoA(){
        return ProductDto.builder()
                .name("Telefon")
                .description("Akıllı Telefon")
//                .brand(createTestBrandDtoA())
//                .images(List.of(createTestProductImageDtoA(),createTestProductImageDtoB()))
//                .category(createTestCategoryDtoA())
                .averageScoreOutOf100(11.2)
                .priceCents(10000)
                .taxPer1000(10)
                .discountPer1000(100)
                .build();
    }

}