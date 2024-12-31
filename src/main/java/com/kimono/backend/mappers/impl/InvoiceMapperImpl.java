package com.kimono.backend.mappers.impl;

import com.kimono.backend.domain.dto.InvoiceDto;
import com.kimono.backend.domain.entities.InvoiceEntity;
import com.kimono.backend.domain.entities.CustomerEntity;
import com.kimono.backend.domain.entities.ProductSnapshotEntity;
import com.kimono.backend.mappers.Mapper;
import com.kimono.backend.repositories.CustomerRepository;
import com.kimono.backend.repositories.ProductSnapshotRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class InvoiceMapperImpl implements Mapper<InvoiceEntity, InvoiceDto> {

    private final CustomerRepository customerRepository;
    private final ProductSnapshotRepository productSnapshotRepository;

    public InvoiceMapperImpl(CustomerRepository customerRepository, ProductSnapshotRepository productSnapshotRepository) {
        this.customerRepository = customerRepository;
        this.productSnapshotRepository = productSnapshotRepository;
    }

    @Override
    public InvoiceDto mapTo(InvoiceEntity entity) {
        if (entity == null) {
            return null;
        }

        return InvoiceDto.builder()
                .id(entity.getId())
                .customerId(entity.getCustomer() != null ? entity.getCustomer().getId() : null)
                .invoiceNumber(entity.getInvoiceNumber())
                .invoiceAddress(entity.getInvoiceAddress())
                .invoiceName(entity.getInvoiceName())
                .productSnapshotIds(entity.getProductSnapshotEntities() != null ?
                        entity.getProductSnapshotEntities().stream()
                                .map(ProductSnapshotEntity::getId)
                                .collect(Collectors.toList()) : null)
                .paymentMethod(entity.getPaymentMethod())
                .totalAmountCents(entity.getTotalAmountCents())
                .build();
    }

    @Override
    public InvoiceEntity mapFrom(InvoiceDto dto) {
        if (dto == null) {
            return null;
        }

        CustomerEntity customer = null;
        if (dto.getCustomerId() != null) {
            customer = customerRepository.findById(dto.getCustomerId())
                    .orElseThrow(() -> new IllegalArgumentException("Customer not found with id: " + dto.getCustomerId()));
        }

        List<ProductSnapshotEntity> productSnapshots = null;
        if (dto.getProductSnapshotIds() != null) {
            productSnapshots = dto.getProductSnapshotIds().stream()
                    .map(snapshotId -> productSnapshotRepository.findById(snapshotId)
                            .orElseThrow(() -> new IllegalArgumentException("Product snapshot not found with id: " + snapshotId)))
                    .collect(Collectors.toList());
        }

        return InvoiceEntity.builder()
                .id(dto.getId())
                .customer(customer)
                .invoiceNumber(dto.getInvoiceNumber())
                .invoiceAddress(dto.getInvoiceAddress())
                .invoiceName(dto.getInvoiceName())
                .productSnapshotEntities(productSnapshots)
                .paymentMethod(dto.getPaymentMethod())
                .totalAmountCents(dto.getTotalAmountCents())
                .build();
    }
}
