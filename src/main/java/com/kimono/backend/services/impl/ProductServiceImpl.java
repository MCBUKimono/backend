package com.kimono.backend.services.impl;

import com.kimono.backend.domain.entities.ProductEntity;
import com.kimono.backend.repositories.ProductRepository;
import com.kimono.backend.services.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductEntity save(ProductEntity productEntity) {
        return productRepository.save(productEntity);
    }

    @Override
    public List<ProductEntity> findByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<ProductEntity> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Optional<ProductEntity> findById(Integer id) {
        return productRepository.findById(id);
    }

    @Override
    public void deleteProductById(Integer id) {
        if(doesNotExist(id)){
            throw new EntityNotFoundException();
        }
        productRepository.deleteById(id);
    }

    @Override
    public boolean doesNotExist(Integer id) {
        return !productRepository.existsById(id);
    }

    @Override
    public ProductEntity partialUpdate(Integer id, ProductEntity productEntity) {
        return productRepository.findById(id).map(existingProduct -> {
            Optional.ofNullable(productEntity.getName()).ifPresent(existingProduct::setName);
            Optional.ofNullable(productEntity.getDescription()).ifPresent(existingProduct::setDescription);
            Optional.ofNullable(productEntity.getBrand()).ifPresent(existingProduct::setBrand);
            Optional.ofNullable(productEntity.getImages()).ifPresent(existingProduct::setImages);
            Optional.ofNullable(productEntity.getCategory()).ifPresent(existingProduct::setCategory);
            Optional.ofNullable(productEntity.getAverageScoreOutOf100()).ifPresent(existingProduct::setAverageScoreOutOf100);//todo:Puanlar update edilemez. puanları çekme şekli değişmeli
            Optional.ofNullable(productEntity.getPriceCents()).ifPresent(existingProduct::setPriceCents);
            Optional.ofNullable(productEntity.getTaxPer1000()).ifPresent(existingProduct::setTaxPer1000);
            Optional.ofNullable(productEntity.getDiscountPer1000()).ifPresent(existingProduct::setDiscountPer1000);
            return productRepository.save(existingProduct);
        }).orElseThrow(() -> new RuntimeException("Product does not exist"));
    }
}













