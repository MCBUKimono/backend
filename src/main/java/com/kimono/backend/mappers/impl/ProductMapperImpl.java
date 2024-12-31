package com.kimono.backend.mappers.impl;

import com.kimono.backend.domain.dto.ProductDto;
import com.kimono.backend.domain.entities.ProductEntity;
import com.kimono.backend.domain.entities.BrandEntity;
import com.kimono.backend.domain.entities.CategoryEntity;
import com.kimono.backend.domain.entities.ProductImageEntity;
import com.kimono.backend.mappers.Mapper;
import com.kimono.backend.repositories.BrandRepository;
import com.kimono.backend.repositories.CategoryRepository;
import com.kimono.backend.repositories.ProductImageRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapperImpl implements Mapper<ProductEntity, ProductDto> {

    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final ProductImageRepository productImageRepository;

    public ProductMapperImpl(BrandRepository brandRepository, CategoryRepository categoryRepository, ProductImageRepository productImageRepository) {
        this.brandRepository = brandRepository;
        this.categoryRepository = categoryRepository;
        this.productImageRepository = productImageRepository;
    }

    @Override
    public ProductDto mapTo(ProductEntity entity) {
        if (entity == null) {
            return null;
        }

        return ProductDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .brandId(entity.getBrand() != null ? entity.getBrand().getId() : null)
                .imagesId(entity.getImages() != null ?
                        entity.getImages().stream()
                                .map(ProductImageEntity::getId)
                                .collect(Collectors.toList()) : null)
                .categoryId(entity.getCategory() != null ? entity.getCategory().getId() : null)
                .averageScoreOutOf100(entity.getAverageScoreOutOf100())
                .priceCents(entity.getPriceCents())
                .taxPer1000(entity.getTaxPer1000())
                .discountPer1000(entity.getDiscountPer1000())
                .build();
    }

    @Override
    public ProductEntity mapFrom(ProductDto dto) {
        if (dto == null) {
            return null;
        }

        BrandEntity brand = null;
        if (dto.getBrandId() != null) {
            brand = brandRepository.findById(dto.getBrandId())
                    .orElseThrow(() -> new IllegalArgumentException("Brand not found with id: " + dto.getBrandId()));
        }

        CategoryEntity category = null;
        if (dto.getCategoryId() != null) {
            category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("Category not found with id: " + dto.getCategoryId()));
        }

        List<ProductImageEntity> images = null;
        if (dto.getImagesId() != null) {
            images = dto.getImagesId().stream()
                    .map(imageId -> productImageRepository.findById(imageId)
                            .orElseThrow(() -> new IllegalArgumentException("Product image not found with id: " + imageId)))
                    .collect(Collectors.toList());
        }

        return ProductEntity.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .brand(brand)
                .images(images)
                .category(category)
                .averageScoreOutOf100(dto.getAverageScoreOutOf100())
                .priceCents(dto.getPriceCents())
                .taxPer1000(dto.getTaxPer1000())
                .discountPer1000(dto.getDiscountPer1000())
                .build();
    }
}
