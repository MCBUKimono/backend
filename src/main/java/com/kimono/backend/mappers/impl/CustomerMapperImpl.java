package com.kimono.backend.mappers.impl;

import com.kimono.backend.domain.dto.CustomerDto;
import com.kimono.backend.domain.entities.CustomerEntity;
import com.kimono.backend.domain.entities.ProductEntity;
import com.kimono.backend.mappers.Mapper;
import com.kimono.backend.repositories.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomerMapperImpl implements Mapper<CustomerEntity, CustomerDto> {

    private final ProductRepository productRepository;

    public CustomerMapperImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public CustomerDto mapTo(CustomerEntity entity) {
        if (entity == null) {
            return null;
        }

        return CustomerDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .passwordHash(entity.getPasswordHash())
                .favoriteProductIds(entity.getFavoriteProducts() != null ?
                        entity.getFavoriteProducts().stream()
                                .map(ProductEntity::getId)
                                .collect(Collectors.toList()) : null)
                .build();
    }

    @Override
    public CustomerEntity mapFrom(CustomerDto dto) {
        if (dto == null) {
            return null;
        }

        List<ProductEntity> favoriteProducts = null;
        if (dto.getFavoriteProductIds() != null) {
            favoriteProducts = dto.getFavoriteProductIds().stream()
                    .map(productId -> productRepository.findById(productId)
                            .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + productId)))
                    .collect(Collectors.toList());
        }

        return CustomerEntity.builder()
                .id(dto.getId())
                .name(dto.getName())
                .passwordHash(dto.getPasswordHash())
                .favoriteProducts(favoriteProducts)
                .build();
    }
}
