package com.kimono.backend.TestSeed;

import com.kimono.backend.domain.dto.*;
import com.kimono.backend.domain.entities.*;
import com.kimono.backend.domain.enums.OrderStatus;
import com.kimono.backend.domain.enums.PaymentMethod;

import java.time.LocalDateTime;
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

    public static CustomerEntity createTestCustomerEntity() {
        return CustomerEntity.builder()
                .name("John Doe")
                .passwordHash("hashedpassword")
                .favoriteProducts(null) // Test senaryosunda gerekirse bir liste eklenebilir
                .orders(null) // Test senaryosunda gerekirse bir liste eklenebilir
                .build();
    }

    public static CustomerDto createTestCustomerDto() {
        return CustomerDto.builder()
                .name("John Doe")
                .passwordHash("hashedpassword")
                .favoriteProductIds(null) // Test senaryosunda gerekirse bir liste eklenebilir
                .build();
    }
    public static ReviewEntity createTestReviewEntity() {
        return ReviewEntity.builder()
                .product(null) // Test bir ProductEntity kullanabilirsiniz
                .customer(null) // Test bir CustomerEntity kullanabilirsiniz
                .images(List.of()) // Testte gerekirse ReviewImageEntity eklenebilir
                .text("This is a test review.")
                .scoreOutOf100(85)
                .build();
    }

    // ReviewDto için test verisi oluşturma
    public static ReviewDto createTestReviewDto() {
        return ReviewDto.builder()
                .productId(null) // Mevcut bir ProductEntity ID kullanabilirsiniz
                .customerId(null) // Mevcut bir CustomerEntity ID kullanabilirsiniz
                .imageIds(null) // Testte gerekirse ReviewImageEntity ID'leri eklenebilir
                .text("This is a test review DTO.")
                .scoreOutOf100(85)
                .build();
    }
    /*public static ProductEntity createTestProductEntityA() {
        return ProductEntity.builder()
                .name("Test Product")
                .description("This is a test product.")
                .priceCents(10000)
                .taxPer1000(100)
                .discountPer1000(500)
                .averageScoreOutOf100(80.0)
                .build();
    }*/

    /*// CustomerEntity için test verisi oluşturma
    public static CustomerEntity createTestCustomerEntityA() {
        return CustomerEntity.builder()
                .name("John Doe")
                .passwordHash("hashedpassword")
                .favoriteProducts(List.of()) // Testte gerekirse ProductEntity eklenebilir
                .orders(List.of()) // Testte gerekirse OrderEntity eklenebilir
                .build();
    }*/

    public static InvoiceEntity createTestInvoiceEntity() {
        return InvoiceEntity.builder()
                .customer(null)
                .invoiceNumber(123456)
                .invoiceAddress("123 Test St")
                .invoiceName("John Doe")
                .paymentMethod(PaymentMethod.CREDIT_CARD)
                .totalAmountCents(15000)
                .productSnapshotEntities(null)
                .build();
    }

    public static InvoiceDto createTestInvoiceDto() {
        return InvoiceDto.builder()
                .customerId(null)
                .invoiceNumber(123456)
                .invoiceAddress("123 Test St")
                .invoiceName("John Doe")
                .paymentMethod(PaymentMethod.CREDIT_CARD)
                .totalAmountCents(15000)
                .productSnapshotIds(null)
                .build();
    }

    /*public static ProductSnapshotEntity createTestProductSnapshotEntity() {
        return ProductSnapshotEntity.builder()
                .product(createTestProductA())
                .priceCents(10000)
                .taxPer1000(200)
                .discountPer1000(500)
                .build();
    }*/

    public static ProductSnapshotEntity createTestProductSnapshotEntity() {
        return ProductSnapshotEntity.builder()
                .product(null)
                .invoice(null) // Gerekirse bir InvoiceEntity eklenebilir
                .priceCents(10000)
                .taxPer1000(200)
                .discountPer1000(500)
                .build();
    }

    public static ProductSnapshotDto createTestProductSnapshotDto() {
        return ProductSnapshotDto.builder()
                .productId(null)
                .invoiceId(null) // Gerekirse mevcut bir InvoiceEntity ID kullanabilirsiniz
                .priceCents(10000)
                .taxPer1000(200)
                .discountPer1000(500)
                .build();
    }

}