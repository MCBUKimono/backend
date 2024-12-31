package com.kimono.backend.mappers.impl;

import com.kimono.backend.domain.dto.ProductSnapshotDto;
import com.kimono.backend.domain.entities.ProductSnapshotEntity;
import com.kimono.backend.domain.entities.ProductEntity;
import com.kimono.backend.domain.entities.InvoiceEntity;
import com.kimono.backend.mappers.Mapper;
import com.kimono.backend.repositories.ProductRepository;
import com.kimono.backend.repositories.InvoiceRepository;
import org.springframework.stereotype.Component;

@Component
public class ProductSnapshotMapperImpl implements Mapper<ProductSnapshotEntity, ProductSnapshotDto> {

    private final ProductRepository productRepository;
    private final InvoiceRepository invoiceRepository;

    public ProductSnapshotMapperImpl(ProductRepository productRepository, InvoiceRepository invoiceRepository) {
        this.productRepository = productRepository;
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public ProductSnapshotDto mapTo(ProductSnapshotEntity entity) {
        if (entity == null) {
            return null;
        }

        return ProductSnapshotDto.builder()
                .id(entity.getId())
                .productId(entity.getProduct() != null ? entity.getProduct().getId() : null)
                .invoiceId(entity.getInvoice() != null ? entity.getInvoice().getId() : null)
                .priceCents(entity.getPriceCents())
                .taxPer1000(entity.getTaxPer1000())
                .discountPer1000(entity.getDiscountPer1000())
                .build();
    }

    @Override
    public ProductSnapshotEntity mapFrom(ProductSnapshotDto dto) {
        if (dto == null) {
            return null;
        }

        ProductEntity product = null;
        if (dto.getProductId() != null) {
            product = productRepository.findById(dto.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + dto.getProductId()));
        }

        InvoiceEntity invoice = null;
        if (dto.getInvoiceId() != null) {
            invoice = invoiceRepository.findById(dto.getInvoiceId())
                    .orElseThrow(() -> new IllegalArgumentException("Invoice not found with id: " + dto.getInvoiceId()));
        }

        return ProductSnapshotEntity.builder()
                .id(dto.getId())
                .product(product)
                .invoice(invoice)
                .priceCents(dto.getPriceCents())
                .taxPer1000(dto.getTaxPer1000())
                .discountPer1000(dto.getDiscountPer1000())
                .build();
    }
}
