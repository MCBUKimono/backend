package com.kimono.backend.controllers;

import com.kimono.backend.domain.dto.ProductImageDto;
import com.kimono.backend.domain.entities.ProductImageEntity;
import com.kimono.backend.mappers.Mapper;
import com.kimono.backend.services.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product-image")
public class ProductImageController {

    private final ProductImageService productImageService;
    private final Mapper<ProductImageEntity, ProductImageDto> mapper;

    @Autowired
    public ProductImageController(ProductImageService productImageService, Mapper<ProductImageEntity, ProductImageDto> mapper) {
        this.productImageService = productImageService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<ProductImageDto> createProductImage(@RequestBody ProductImageDto productImageDto) {
        ProductImageEntity productImageEntity = mapper.mapFrom(productImageDto);
        ProductImageEntity savedProductImage = productImageService.save(productImageEntity);
        return new ResponseEntity<>(mapper.mapTo(savedProductImage), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProductImageDto>> getAllProductImages() {
        List<ProductImageEntity> productImages = productImageService.findAll();
        List<ProductImageDto> productImageDtos = productImages.stream().map(mapper::mapTo).collect(Collectors.toList());
        return new ResponseEntity<>(productImageDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductImageDto> getProductImageById(@PathVariable Integer id) {
        ProductImageEntity productImage = productImageService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Image not found"));
        return new ResponseEntity<>(mapper.mapTo(productImage), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductImageDto> updateProductImage(@PathVariable Integer id, @RequestBody ProductImageDto productImageDto) {
        if (productImageService.doesNotExist(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Image not found");
        }

        productImageDto.setId(id);
        ProductImageEntity updatedProductImage = productImageService.save(mapper.mapFrom(productImageDto));
        return new ResponseEntity<>(mapper.mapTo(updatedProductImage), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductImageDto> partiallyUpdateProductImage(@PathVariable Integer id, @RequestBody ProductImageDto productImageDto) {
        if (productImageService.doesNotExist(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Image not found");
        }

        ProductImageEntity updatedProductImage = productImageService.partialUpdate(id, mapper.mapFrom(productImageDto));
        return new ResponseEntity<>(mapper.mapTo(updatedProductImage), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProductImageById(@PathVariable Integer id) {
        try {
            productImageService.deleteProductImageById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
