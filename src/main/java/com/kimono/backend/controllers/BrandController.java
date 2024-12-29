package com.kimono.backend.controllers;

import com.kimono.backend.domain.dto.BrandDto;
import com.kimono.backend.domain.entities.BrandEntity;
import com.kimono.backend.mappers.Mapper;
import com.kimono.backend.services.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class BrandController {

    private final BrandService brandService;
    private final Mapper<BrandEntity, BrandDto> mapper;

    @Autowired
    public BrandController(BrandService brandService, Mapper<BrandEntity, BrandDto> mapper) {
        this.brandService = brandService;
        this.mapper = mapper;
    }

    @PostMapping(path = "/brand")
    public ResponseEntity<BrandDto> createBrand(@RequestBody BrandDto brandDto) {
        BrandEntity brandEntity = mapper.mapFrom(brandDto);
        BrandEntity savedBrandEntity = brandService.save(brandEntity);
        return new ResponseEntity<>(mapper.mapTo(savedBrandEntity), HttpStatus.CREATED);
    }

    @GetMapping(path = "/brand")
    public ResponseEntity<List<BrandDto>> getAllBrands() {
        List<BrandEntity> brandEntities = brandService.findAll();
        List<BrandDto> brandDtos = brandEntities.stream().map(mapper::mapTo).collect(Collectors.toList());
        return new ResponseEntity<>(brandDtos, HttpStatus.OK);
    }

    @GetMapping(path = "/brand/{id}")
    public ResponseEntity<BrandDto> getBrandById(@PathVariable Integer id) {
        BrandEntity brandEntity = brandService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Brand not found"));
        return new ResponseEntity<>(mapper.mapTo(brandEntity), HttpStatus.OK);
    }

    @GetMapping(path = "/brand/name/{name}")
    public ResponseEntity<List<BrandDto>> getBrandsByName(@PathVariable String name) {
        List<BrandEntity> brandEntities = brandService.findByName(name);
        List<BrandDto> brandDtos = brandEntities.stream().map(mapper::mapTo).collect(Collectors.toList());
        return new ResponseEntity<>(brandDtos, HttpStatus.OK);
    }

    @DeleteMapping(path = "/brand/{id}")
    public ResponseEntity<?> deleteBrandById(@PathVariable Integer id) {
        try {
            brandService.deleteBrandById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping(path = "/brand/{id}")
    public ResponseEntity<BrandDto> updateBrand(@PathVariable Integer id, @RequestBody BrandDto brandDto) {
        if (brandService.doesNotExist(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Brand not found");
        }

        brandDto.setId(id);
        BrandEntity brandEntity = mapper.mapFrom(brandDto);
        BrandEntity updatedBrandEntity = brandService.save(brandEntity);
        return new ResponseEntity<>(mapper.mapTo(updatedBrandEntity), HttpStatus.OK);
    }

    @PatchMapping(path = "/brand/{id}")
    public ResponseEntity<BrandDto> updateBrandPartial(@PathVariable Integer id, @RequestBody BrandDto brandDto) {
        if (!brandService.doesNotExist(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Brand not found");
        }

        BrandEntity brandEntity = mapper.mapFrom(brandDto);
        BrandEntity updatedBrandEntity = brandService.partialUpdate(id, brandEntity);
        return new ResponseEntity<>(mapper.mapTo(updatedBrandEntity), HttpStatus.OK);
    }
}
