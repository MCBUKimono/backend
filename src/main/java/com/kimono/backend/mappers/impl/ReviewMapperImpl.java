package com.kimono.backend.mappers.impl;

import com.kimono.backend.domain.dto.ReviewDto;
import com.kimono.backend.domain.entities.ReviewEntity;
import com.kimono.backend.domain.entities.CustomerEntity;
import com.kimono.backend.domain.entities.ProductEntity;
import com.kimono.backend.domain.entities.ReviewImageEntity;
import com.kimono.backend.mappers.Mapper;
import com.kimono.backend.repositories.CustomerRepository;
import com.kimono.backend.repositories.ProductRepository;
import com.kimono.backend.repositories.ReviewImageRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReviewMapperImpl implements Mapper<ReviewEntity, ReviewDto> {

    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final ReviewImageRepository reviewImageRepository;

    public ReviewMapperImpl(CustomerRepository customerRepository, ProductRepository productRepository, ReviewImageRepository reviewImageRepository) {
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.reviewImageRepository = reviewImageRepository;
    }

    @Override
    public ReviewDto mapTo(ReviewEntity entity) {
        if (entity == null) {
            return null;
        }

        return ReviewDto.builder()
                .id(entity.getId())
                .productId(entity.getProduct() != null ? entity.getProduct().getId() : null)
                .customerId(entity.getCustomer() != null ? entity.getCustomer().getId() : null)
                .imageIds(entity.getImages() != null ?
                        entity.getImages().stream()
                                .map(ReviewImageEntity::getId)
                                .collect(Collectors.toList()) : null)
                .text(entity.getText())
                .scoreOutOf100(entity.getScoreOutOf100())
                .build();
    }

    @Override
    public ReviewEntity mapFrom(ReviewDto dto) {
        if (dto == null) {
            return null;
        }

        CustomerEntity customer = null;
        if (dto.getCustomerId() != null) {
            customer = customerRepository.findById(dto.getCustomerId())
                    .orElseThrow(() -> new IllegalArgumentException("Customer not found with id: " + dto.getCustomerId()));
        }

        ProductEntity product = null;
        if (dto.getProductId() != null) {
            product = productRepository.findById(dto.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + dto.getProductId()));
        }

        List<ReviewImageEntity> images = null;
        if (dto.getImageIds() != null) {
            images = dto.getImageIds().stream()
                    .map(imageId -> reviewImageRepository.findById(imageId)
                            .orElseThrow(() -> new IllegalArgumentException("Review image not found with id: " + imageId)))
                    .collect(Collectors.toList());
        }

        return ReviewEntity.builder()
                .id(dto.getId())
                .product(product)
                .customer(customer)
                .images(images)
                .text(dto.getText())
                .scoreOutOf100(dto.getScoreOutOf100())
                .build();
    }
}
