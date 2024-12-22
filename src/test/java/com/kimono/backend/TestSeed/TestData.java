package com.kimono.backend.TestSeed;

import com.kimono.backend.domain.entities.*;
import com.kimono.backend.domain.enums.OrderStatus;

public final class TestData {

    public static BrandEntity createBrandEntity1() {
        return BrandEntity.builder()
                .id(null)
                .name("Adidas")
                .build();
    }

    public static BrandEntity createBrandEntity2() {
        return BrandEntity.builder()
                .id(null)
                .name("Nike")
                .build();
    }

    public static BrandEntity createBrandEntity3() {
        return BrandEntity.builder()
                .id(null)
                .name("Alienware")
                .build();
    }

    public static BrandEntity createBrandEntity4() {
        return BrandEntity.builder()
                .id(null)
                .name("BMW")
                .build();
    }

    public static CategoryEntity createCategoryEntity1() {
        return CategoryEntity.builder()
                .id(null)
                .name("Electronics")
                .products(null)
                .build();
    }

    public static CategoryEntity createCategoryEntity2() {
        return CategoryEntity.builder()
                .id(null)
                .name("Clothing")
                .products(null)
                .build();
    }

    public static CategoryEntity createCategoryEntity3() {
        return CategoryEntity.builder()
                .id(null)
                .name("Home Appliances")
                .products(null)
                .build();
    }



    public static CustomerEntity createCustomerEntity1() {
        return CustomerEntity.builder()
                .id(null)
                .name("John Doe")
                .passwordHash("hashedpassword123")
                .build();
    }

    public static CustomerEntity createCustomerEntity2() {
        return CustomerEntity.builder()
                .id(null)
                .name("Jane Smith")
                .passwordHash("hashedpassword456")
                .build();
    }

    public static CustomerEntity createCustomerEntity3() {
        return CustomerEntity.builder()
                .id(null)
                .name("Alice Brown")
                .passwordHash("hashedpassword789")
                .build();
    }


    public static InvoiceEntity createInvoiceEntity1() {
        return InvoiceEntity.builder()
                .id(null)
                .invoiceNumber(1001)
                .invoiceName("Invoice 1")
                .build();
    }

    public static InvoiceEntity createInvoiceEntity2() {
        return InvoiceEntity.builder()
                .id(null)
                .invoiceNumber(1002)
                .invoiceName("Invoice 2")
                .build();
    }


    public static OrderEntity createOrderEntity1() {
        return OrderEntity.builder()
                .id(null)
                .shippingAddress("123 Main St")
                .status(OrderStatus.SHIPPED)
                .build();
    }

    public static OrderEntity createOrderEntity2() {
        return OrderEntity.builder()
                .id(null)
                .shippingAddress("456 Elm St")
                .status(OrderStatus.DELIVERED)
                .build();
    }


    public static ProductImageEntity createProductImageEntity1() {
        return ProductImageEntity.builder()
                .id(null)
                .displayOrder(1)
                .build();
    }

    public static ProductImageEntity createProductImageEntity2() {
        return ProductImageEntity.builder()
                .id(null)
                .displayOrder(2)
                .build();
    }


    public static ProductEntity createProductEntity1() {
        return ProductEntity.builder()
                .id(null)
                .name("Smartphone")
                .averageScoreOutOf100(90.0)
                .build();
    }

    public static ProductEntity createProductEntity2() {
        return ProductEntity.builder()
                .id(null)
                .name("Laptop")
                .averageScoreOutOf100(85.0)
                .build();
    }


    public static ProductSnapshotEntity createProductSnapshotEntity1() {
        return ProductSnapshotEntity.builder()
                .id(null)
                .build();
    }


    public static ReviewImageEntity createReviewImageEntity1() {
        return ReviewImageEntity.builder()
                .id(null)
                .displayOrder(1)
                .build();
    }



    public static ReviewEntity createReviewEntity1() {
        return ReviewEntity.builder()
                .id(null)
                .text("Great product")
                .scoreOutOf100(95)
                .build();
    }

    public static ReviewEntity createReviewEntity2() {
        return ReviewEntity.builder()
                .id(null)
                .text("Not bad")
                .scoreOutOf100(70)
                .build();
    }


    public static SessionEntity createSessionEntity1() {
        return SessionEntity.builder()
                .id(null)
                .token("token123")
                .build();
    }

    public static SessionEntity createSessionEntity2() {
        return SessionEntity.builder()
                .id(null)
                .token("token456")
                .build();
    }



}