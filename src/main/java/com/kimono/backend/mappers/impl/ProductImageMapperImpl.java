package com.kimono.backend.mappers.impl;

import com.kimono.backend.domain.dto.ProductImageDto;
import com.kimono.backend.domain.entities.ProductImageEntity;
import com.kimono.backend.domain.entities.ProductEntity;
import com.kimono.backend.mappers.Mapper;
import com.kimono.backend.repositories.ProductRepository;
import org.springframework.stereotype.Component;

@Component
public class ProductImageMapperImpl implements Mapper<ProductImageEntity, ProductImageDto> {

    private final ProductRepository productRepository;

    public ProductImageMapperImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductImageDto mapTo(ProductImageEntity entity) {
        if (entity == null) {
            return null;
        }

        return ProductImageDto.builder()
                .id(entity.getId())
                .displayOrder(entity.getDisplayOrder())
                .productId(entity.getProduct() != null ? entity.getProduct().getId() : null)
                .url(entity.getUrl())
                .build();
    }

    @Override
    public ProductImageEntity mapFrom(ProductImageDto dto) {
        if (dto == null) {
            return null;
        }

        ProductEntity product = null;
        if (dto.getProductId() != null) {
            product = productRepository.findById(dto.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + dto.getProductId()));
        }

        return ProductImageEntity.builder()
                .id(dto.getId())
                .displayOrder(dto.getDisplayOrder())
                .product(product)
                .url(dto.getUrl())
                .build();
    }
}
