package com.kimono.backend.controllers;

import com.kimono.backend.domain.dto.ProductDto;
import com.kimono.backend.domain.entities.ProductEntity;
import com.kimono.backend.mappers.Mapper;
import com.kimono.backend.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProductController {

    private final ProductService productService;
    private final Mapper<ProductEntity, ProductDto> mapper;

    @Autowired
    public ProductController(ProductService productService, Mapper<ProductEntity, ProductDto> mapper) {
        this.productService = productService;
        this.mapper = mapper;
    }

    @PostMapping(path = "/product")
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        productDto.setId(null);
        ProductEntity productEntity = mapper.mapFrom(productDto);
        ProductEntity savedProductEntity = productService.save(productEntity);
        return new ResponseEntity<>(mapper.mapTo(savedProductEntity), HttpStatus.CREATED);
    }

    @GetMapping(path = "/product")
    public ResponseEntity<List<ProductDto>> getProducts() {
        List<ProductEntity> productEntities = productService.findAll();

        List<ProductDto> productDtos = productEntities.stream().map(mapper::mapTo).collect(Collectors.toList());

        return new ResponseEntity<>(productDtos, HttpStatus.OK);
    }

    @GetMapping(path = "/product/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Integer id) {
        ProductEntity productEntity = productService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        return new ResponseEntity<>(mapper.mapTo(productEntity), HttpStatus.OK);
    }

    @GetMapping(path = "/product/name/{name}")
    public ResponseEntity<List<ProductDto>> getProductsByName(@PathVariable String name) {
        List<ProductEntity> productEntities = productService.findByName(name);

        List<ProductDto> productDtos = productEntities.stream().map(mapper::mapTo).collect(Collectors.toList());
        return new ResponseEntity<>(productDtos, HttpStatus.OK);
    }


    @DeleteMapping(path = "/product/{id}")
    public ResponseEntity<?> deleteProductById(@PathVariable Integer id) {
        try {
            productService.deleteProductById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping(path = "/product/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Integer id, @RequestBody ProductDto productDto) {
        if(productService.doesNotExist(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }

        productDto.setId(id);
        ProductEntity productEntity = mapper.mapFrom(productDto);
        ProductEntity updatedProductEntity = productService.save(productEntity);
        return new ResponseEntity<>(mapper.mapTo(updatedProductEntity), HttpStatus.OK);
    }

    @PatchMapping(path = "/product/{id}")
    public ResponseEntity<ProductDto> updateProductPartial(@PathVariable("id") Integer id, @RequestBody ProductDto productDto) {
        if(productService.doesNotExist(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }
        ProductEntity productEntity = mapper.mapFrom(productDto);
        ProductEntity updatedProductEntity = productService.partialUpdate(id, productEntity);
        return new ResponseEntity<>(mapper.mapTo(updatedProductEntity), HttpStatus.OK);
    }
}
