package com.kimono.backend.services;

import com.kimono.backend.domain.entities.ProductImageEntity;

import java.util.List;
import java.util.Optional;

public interface ProductImageService {
    ProductImageEntity save(ProductImageEntity productEntity);

    List<ProductImageEntity> findAll();

    Optional<ProductImageEntity> findById(Integer id);

    void deleteProductImageById(Integer id);

    boolean doesNotExist(Integer id);

    ProductImageEntity partialUpdate(Integer id, ProductImageEntity productImageEntity);
}
