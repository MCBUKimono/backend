package com.kimono.backend.mapper;

import com.kimono.backend.domain.dto.ProductSnapshotDto;
import com.kimono.backend.domain.entities.InvoiceEntity;
import com.kimono.backend.domain.entities.ProductEntity;
import com.kimono.backend.domain.entities.ProductSnapshotEntity;
import com.kimono.backend.mappers.impl.ProductSnapshotMapperImpl;
import com.kimono.backend.repositories.InvoiceRepository;
import com.kimono.backend.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class ProductSnapshotMapperImplTest {

    private ProductSnapshotMapperImpl productSnapshotMapper;
    private ProductRepository productRepository;
    private InvoiceRepository invoiceRepository;

    @BeforeEach
    void setUp() {
        productRepository = Mockito.mock(ProductRepository.class);
        invoiceRepository = Mockito.mock(InvoiceRepository.class);
        productSnapshotMapper = new ProductSnapshotMapperImpl(productRepository, invoiceRepository);
    }

    @Test
    void testMapToProductSnapshotDto() {
        // Given
        ProductEntity product = ProductEntity.builder().id(1).name("Product 1").build();
        InvoiceEntity invoice = InvoiceEntity.builder().id(2).invoiceNumber(1001).build();

        ProductSnapshotEntity productSnapshotEntity = ProductSnapshotEntity.builder()
                .id(3)
                .product(product)
                .invoice(invoice)
                .priceCents(10000)
                .taxPer1000(200)
                .discountPer1000(500)
                .build();

        // When
        ProductSnapshotDto productSnapshotDto = productSnapshotMapper.mapTo(productSnapshotEntity);

        // Then
        assertNotNull(productSnapshotDto);
        assertEquals(productSnapshotEntity.getId(), productSnapshotDto.getId());
        assertEquals(product.getId(), productSnapshotDto.getProductId());
        assertEquals(invoice.getId(), productSnapshotDto.getInvoiceId());
        assertEquals(productSnapshotEntity.getPriceCents(), productSnapshotDto.getPriceCents());
        assertEquals(productSnapshotEntity.getTaxPer1000(), productSnapshotDto.getTaxPer1000());
        assertEquals(productSnapshotEntity.getDiscountPer1000(), productSnapshotDto.getDiscountPer1000());
    }

    @Test
    void testMapToProductSnapshotDtoWithNull() {
        // When
        ProductSnapshotDto productSnapshotDto = productSnapshotMapper.mapTo(null);

        // Then
        assertNull(productSnapshotDto);
    }

    @Test
    void testMapFromProductSnapshotDto() {
        // Given
        ProductSnapshotDto productSnapshotDto = ProductSnapshotDto.builder()
                .id(3)
                .productId(1)
                .invoiceId(2)
                .priceCents(10000)
                .taxPer1000(200)
                .discountPer1000(500)
                .build();

        ProductEntity product = ProductEntity.builder().id(1).name("Product 1").build();
        InvoiceEntity invoice = InvoiceEntity.builder().id(2).invoiceNumber(1001).build();

        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        when(invoiceRepository.findById(2)).thenReturn(Optional.of(invoice));

        // When
        ProductSnapshotEntity productSnapshotEntity = productSnapshotMapper.mapFrom(productSnapshotDto);

        // Then
        assertNotNull(productSnapshotEntity);
        assertEquals(productSnapshotDto.getId(), productSnapshotEntity.getId());
        assertNotNull(productSnapshotEntity.getProduct());
        assertEquals(product.getId(), productSnapshotEntity.getProduct().getId());
        assertNotNull(productSnapshotEntity.getInvoice());
        assertEquals(invoice.getId(), productSnapshotEntity.getInvoice().getId());
        assertEquals(productSnapshotDto.getPriceCents(), productSnapshotEntity.getPriceCents());
        assertEquals(productSnapshotDto.getTaxPer1000(), productSnapshotEntity.getTaxPer1000());
        assertEquals(productSnapshotDto.getDiscountPer1000(), productSnapshotEntity.getDiscountPer1000());
    }

    @Test
    void testMapFromProductSnapshotDtoWithInvalidProduct() {
        // Given
        ProductSnapshotDto productSnapshotDto = ProductSnapshotDto.builder()
                .productId(999)
                .build();

        when(productRepository.findById(999)).thenReturn(Optional.empty());

        // When/Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> productSnapshotMapper.mapFrom(productSnapshotDto));
        assertEquals("Product not found with id: 999", exception.getMessage());
    }

    @Test
    void testMapFromProductSnapshotDtoWithInvalidInvoice() {
        // Given
        ProductSnapshotDto productSnapshotDto = ProductSnapshotDto.builder()
                .invoiceId(999)
                .build();

        when(invoiceRepository.findById(999)).thenReturn(Optional.empty());

        // When/Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> productSnapshotMapper.mapFrom(productSnapshotDto));
        assertEquals("Invoice not found with id: 999", exception.getMessage());
    }

    @Test
    void testMapFromProductSnapshotDtoWithNull() {
        // When
        ProductSnapshotEntity productSnapshotEntity = productSnapshotMapper.mapFrom(null);

        // Then
        assertNull(productSnapshotEntity);
    }
}
