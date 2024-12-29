package com.kimono.backend.services.impl;

import com.kimono.backend.domain.entities.ProductImageEntity;
import com.kimono.backend.repositories.ProductImageRepository;
import com.kimono.backend.services.ProductImageService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductImageServiceImpl implements ProductImageService {

    private final ProductImageRepository productImageRepository;

    public ProductImageServiceImpl(ProductImageRepository productImageRepository) {
        this.productImageRepository = productImageRepository;
    }

    @Override
    public ProductImageEntity save(ProductImageEntity productEntity) {
        return productImageRepository.save(productEntity);
    }

    @Override
    public List<ProductImageEntity> findAll() {
        return productImageRepository.findAll();
    }

    @Override
    public Optional<ProductImageEntity> findById(Integer id) {
        return productImageRepository.findById(id);
    }

    @Override
    public void deleteProductImageById(Integer id) {
        if(doesNotExist(id)){
            throw new EntityNotFoundException();
        }
        productImageRepository.deleteById(id);
    }

    @Override
    public boolean doesNotExist(Integer id) {
        return !productImageRepository.existsById(id);
    }

    @Override
    public ProductImageEntity partialUpdate(Integer id, ProductImageEntity productImageEntity) {
        return productImageRepository.findById(id).map(existingProduct -> {
            Optional.ofNullable(productImageEntity.getUrl()).ifPresent(existingProduct::setUrl);
            Optional.ofNullable(productImageEntity.getProduct()).ifPresent(existingProduct::setProduct);
            Optional.ofNullable(productImageEntity.getDisplayOrder()).ifPresent(existingProduct::setDisplayOrder);
            return productImageRepository.save(existingProduct);
        }).orElseThrow(() -> new RuntimeException("Product image does not exist"));
    }
}
