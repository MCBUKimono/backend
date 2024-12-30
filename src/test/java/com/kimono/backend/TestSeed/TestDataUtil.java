package com.kimono.backend.TestSeed;

import com.kimono.backend.domain.dto.*;
import com.kimono.backend.domain.entities.*;
import com.kimono.backend.domain.enums.OrderStatus;

import java.time.LocalDateTime;


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

    public static ReviewImageEntity createTestReviewImageEntityA(){
        return ReviewImageEntity.builder()
                .review(null)
                .displayOrder(123123)
                .build();
    }
    public static ReviewImageEntity createTestReviewImageEntityB(){
        return ReviewImageEntity.builder()
                .review(null)
                .displayOrder(123123)
                .build();
    }

    public static ReviewImageDto createTestReviewImageDtoA(){
        return ReviewImageDto.builder()
                .reviewId(null)
                .displayOrder(123123)
                .build();
    }

    public static ReviewImageDto createTestReviewImageDtoB(){
        return ReviewImageDto.builder()
                .reviewId(null)
                .displayOrder(123123)
                .build();
    }

    public static SessionEntity createTestSessionEntity() {
        return SessionEntity.builder()
                .customer(null) // Test senaryolarında gerekirse CustomerEntity oluşturulabilir
                .token("test-token")
                .expirationDate(LocalDateTime.now().plusDays(1))
                .build();
    }

    public static SessionDto createTestSessionDto() {
        return SessionDto.builder()
                .customerId(null) // Test senaryolarında gerçek bir müşteri ID kullanılabilir
                .token("test-token")
                .expirationDate(LocalDateTime.now().plusDays(1))
                .build();
    }

    public static OrderEntity createTestOrderEntity() {
        return OrderEntity.builder()
                .shippingAddress("123 Test Street")
                .shippingName("John Doe")
                .trackingCode("TRACK123")
                .status(OrderStatus.READY_FOR_SHIPPING)
                .customer(null) // Gerekirse CustomerEntity ile bağlayabilirsiniz
                .build();
    }

    public static OrderDto createTestOrderDto() {
        return OrderDto.builder()
                .shippingAddress("123 Test Street")
                .shippingName("John Doe")
                .trackingCode("TRACK123")
                .status(OrderStatus.SHIPPED)
                .customerId(null) // Gerekirse müşteri ID ekleyebilirsiniz
                .build();
    }

}