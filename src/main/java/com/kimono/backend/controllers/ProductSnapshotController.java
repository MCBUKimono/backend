package com.kimono.backend.controllers;

import com.kimono.backend.domain.dto.ProductSnapshotDto;
import com.kimono.backend.domain.entities.ProductSnapshotEntity;
import com.kimono.backend.mappers.Mapper;
import com.kimono.backend.services.ProductSnapshotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product-snapshots")
public class ProductSnapshotController {

    private final ProductSnapshotService snapshotService;
    private final Mapper<ProductSnapshotEntity, ProductSnapshotDto> mapper;

    @Autowired
    public ProductSnapshotController(ProductSnapshotService snapshotService, Mapper<ProductSnapshotEntity, ProductSnapshotDto> mapper) {
        this.snapshotService = snapshotService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<ProductSnapshotDto> createSnapshot(@RequestBody ProductSnapshotDto snapshotDto) {
        ProductSnapshotEntity snapshotEntity = mapper.mapFrom(snapshotDto);
        ProductSnapshotEntity savedSnapshot = snapshotService.save(snapshotEntity);
        return new ResponseEntity<>(mapper.mapTo(savedSnapshot), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProductSnapshotDto>> getAllSnapshots() {
        List<ProductSnapshotEntity> snapshots = snapshotService.findAll();
        List<ProductSnapshotDto> snapshotDtos = snapshots.stream().map(mapper::mapTo).collect(Collectors.toList());
        return new ResponseEntity<>(snapshotDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductSnapshotDto> getSnapshotById(@PathVariable Integer id) {
        ProductSnapshotEntity snapshot = snapshotService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Snapshot not found"));
        return new ResponseEntity<>(mapper.mapTo(snapshot), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductSnapshotDto> updateSnapshot(@PathVariable Integer id, @RequestBody ProductSnapshotDto snapshotDto) {
        if (snapshotService.doesNotExist(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Snapshot not found");
        }

        snapshotDto.setId(id);
        ProductSnapshotEntity updatedSnapshot = snapshotService.save(mapper.mapFrom(snapshotDto));
        return new ResponseEntity<>(mapper.mapTo(updatedSnapshot), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSnapshotById(@PathVariable Integer id) {
        try {
            snapshotService.deleteSnapshotById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
