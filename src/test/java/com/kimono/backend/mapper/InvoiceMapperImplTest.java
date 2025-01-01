package com.kimono.backend.mapper;

import com.kimono.backend.domain.dto.InvoiceDto;
import com.kimono.backend.domain.entities.CustomerEntity;
import com.kimono.backend.domain.entities.InvoiceEntity;
import com.kimono.backend.domain.entities.ProductSnapshotEntity;
import com.kimono.backend.domain.enums.PaymentMethod;
import com.kimono.backend.mappers.impl.InvoiceMapperImpl;
import com.kimono.backend.repositories.CustomerRepository;
import com.kimono.backend.repositories.ProductSnapshotRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class InvoiceMapperImplTest {

    private InvoiceMapperImpl invoiceMapper;
    private CustomerRepository customerRepository;
    private ProductSnapshotRepository productSnapshotRepository;

    @BeforeEach
    void setUp() {
        customerRepository = Mockito.mock(CustomerRepository.class);
        productSnapshotRepository = Mockito.mock(ProductSnapshotRepository.class);
        invoiceMapper = new InvoiceMapperImpl(customerRepository, productSnapshotRepository);
    }

    @Test
    void testMapToInvoiceDto() {
        // Given
        CustomerEntity customer = CustomerEntity.builder().id(1).name("John Doe").build();
        ProductSnapshotEntity snapshot = ProductSnapshotEntity.builder().id(2).priceCents(10000).build();

        InvoiceEntity invoiceEntity = InvoiceEntity.builder()
                .id(3)
                .customer(customer)
                .invoiceNumber(12345)
                .invoiceAddress("123 Main St")
                .invoiceName("John's Invoice")
                .productSnapshotEntities(List.of(snapshot))
                .paymentMethod(PaymentMethod.CREDIT_CARD)
                .totalAmountCents(15000)
                .build();

        // When
        InvoiceDto invoiceDto = invoiceMapper.mapTo(invoiceEntity);

        // Then
        assertNotNull(invoiceDto);
        assertEquals(invoiceEntity.getId(), invoiceDto.getId());
        assertEquals(customer.getId(), invoiceDto.getCustomerId());
        assertEquals(invoiceEntity.getInvoiceNumber(), invoiceDto.getInvoiceNumber());
        assertEquals(invoiceEntity.getInvoiceAddress(), invoiceDto.getInvoiceAddress());
        assertEquals(invoiceEntity.getInvoiceName(), invoiceDto.getInvoiceName());
        assertEquals(1, invoiceDto.getProductSnapshotIds().size());
        assertTrue(invoiceDto.getProductSnapshotIds().contains(snapshot.getId()));
        assertEquals(invoiceEntity.getPaymentMethod(), invoiceDto.getPaymentMethod());
        assertEquals(invoiceEntity.getTotalAmountCents(), invoiceDto.getTotalAmountCents());
    }

    @Test
    void testMapToInvoiceDtoWithNull() {
        // When
        InvoiceDto invoiceDto = invoiceMapper.mapTo(null);

        // Then
        assertNull(invoiceDto);
    }

    @Test
    void testMapFromInvoiceDto() {
        // Given
        InvoiceDto invoiceDto = InvoiceDto.builder()
                .id(3)
                .customerId(1)
                .invoiceNumber(12345)
                .invoiceAddress("123 Main St")
                .invoiceName("John's Invoice")
                .productSnapshotIds(List.of(2))
                .paymentMethod(PaymentMethod.CREDIT_CARD)
                .totalAmountCents(15000)
                .build();

        CustomerEntity customer = CustomerEntity.builder().id(1).name("John Doe").build();
        ProductSnapshotEntity snapshot = ProductSnapshotEntity.builder().id(2).priceCents(10000).build();

        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        when(productSnapshotRepository.findById(2)).thenReturn(Optional.of(snapshot));

        // When
        InvoiceEntity invoiceEntity = invoiceMapper.mapFrom(invoiceDto);

        // Then
        assertNotNull(invoiceEntity);
        assertEquals(invoiceDto.getId(), invoiceEntity.getId());
        assertNotNull(invoiceEntity.getCustomer());
        assertEquals(customer.getId(), invoiceEntity.getCustomer().getId());
        assertEquals(invoiceDto.getInvoiceNumber(), invoiceEntity.getInvoiceNumber());
        assertEquals(invoiceDto.getInvoiceAddress(), invoiceEntity.getInvoiceAddress());
        assertEquals(invoiceDto.getInvoiceName(), invoiceEntity.getInvoiceName());
        assertEquals(1, invoiceEntity.getProductSnapshotEntities().size());
        assertTrue(invoiceEntity.getProductSnapshotEntities().contains(snapshot));
        assertEquals(invoiceDto.getPaymentMethod(), invoiceEntity.getPaymentMethod());
        assertEquals(invoiceDto.getTotalAmountCents(), invoiceEntity.getTotalAmountCents());
    }

    @Test
    void testMapFromInvoiceDtoWithInvalidCustomer() {
        // Given
        InvoiceDto invoiceDto = InvoiceDto.builder()
                .customerId(999)
                .build();

        when(customerRepository.findById(999)).thenReturn(Optional.empty());

        // When/Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> invoiceMapper.mapFrom(invoiceDto));
        assertEquals("Customer not found with id: 999", exception.getMessage());
    }

    @Test
    void testMapFromInvoiceDtoWithInvalidProductSnapshot() {
        // Given
        InvoiceDto invoiceDto = InvoiceDto.builder()
                .productSnapshotIds(List.of(999))
                .build();

        when(productSnapshotRepository.findById(999)).thenReturn(Optional.empty());

        // When/Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> invoiceMapper.mapFrom(invoiceDto));
        assertEquals("Product snapshot not found with id: 999", exception.getMessage());
    }

    @Test
    void testMapFromInvoiceDtoWithNull() {
        // When
        InvoiceEntity invoiceEntity = invoiceMapper.mapFrom(null);

        // Then
        assertNull(invoiceEntity);
    }
}
