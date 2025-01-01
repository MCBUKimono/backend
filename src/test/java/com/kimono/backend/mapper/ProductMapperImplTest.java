package com.kimono.backend.mapper;

import com.kimono.backend.domain.dto.ProductDto;
import com.kimono.backend.domain.entities.BrandEntity;
import com.kimono.backend.domain.entities.CategoryEntity;
import com.kimono.backend.domain.entities.ProductEntity;
import com.kimono.backend.domain.entities.ProductImageEntity;
import com.kimono.backend.mappers.impl.ProductMapperImpl;
import com.kimono.backend.repositories.BrandRepository;
import com.kimono.backend.repositories.CategoryRepository;
import com.kimono.backend.repositories.ProductImageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class ProductMapperImplTest {

    private ProductMapperImpl productMapper;
    private BrandRepository brandRepository;
    private CategoryRepository categoryRepository;
    private ProductImageRepository productImageRepository;

    @BeforeEach
    void setUp() {
        brandRepository = Mockito.mock(BrandRepository.class);
        categoryRepository = Mockito.mock(CategoryRepository.class);
        productImageRepository = Mockito.mock(ProductImageRepository.class);
        productMapper = new ProductMapperImpl(brandRepository, categoryRepository, productImageRepository);
    }

    @Test
    void testMapToProductDto() {
        // Given
        BrandEntity brand = BrandEntity.builder().id(1).name("Brand 1").build();
        CategoryEntity category = CategoryEntity.builder().id(2).name("Category 1").build();
        ProductImageEntity image = ProductImageEntity.builder().id(3).url("http://example.com/image.jpg").build();

        ProductEntity productEntity = ProductEntity.builder()
                .id(4)
                .name("Product 1")
                .description("Product Description")
                .brand(brand)
                .category(category)
                .images(List.of(image))
                .averageScoreOutOf100(95.0)
                .priceCents(10000)
                .taxPer1000(200)
                .discountPer1000(500)
                .build();

        // When
        ProductDto productDto = productMapper.mapTo(productEntity);

        // Then
        assertNotNull(productDto);
        assertEquals(productEntity.getId(), productDto.getId());
        assertEquals(productEntity.getName(), productDto.getName());
        assertEquals(productEntity.getDescription(), productDto.getDescription());
        assertEquals(brand.getId(), productDto.getBrandId());
        assertEquals(category.getId(), productDto.getCategoryId());
        assertEquals(1, productDto.getImagesId().size());
        assertTrue(productDto.getImagesId().contains(image.getId()));
        assertEquals(productEntity.getAverageScoreOutOf100(), productDto.getAverageScoreOutOf100());
        assertEquals(productEntity.getPriceCents(), productDto.getPriceCents());
        assertEquals(productEntity.getTaxPer1000(), productDto.getTaxPer1000());
        assertEquals(productEntity.getDiscountPer1000(), productDto.getDiscountPer1000());
    }

    @Test
    void testMapToProductDtoWithNull() {
        // When
        ProductDto productDto = productMapper.mapTo(null);

        // Then
        assertNull(productDto);
    }

    @Test
    void testMapFromProductDto() {
        // Given
        ProductDto productDto = ProductDto.builder()
                .id(4)
                .name("Product 1")
                .description("Product Description")
                .brandId(1)
                .categoryId(2)
                .imagesId(List.of(3))
                .averageScoreOutOf100(95.0)
                .priceCents(10000)
                .taxPer1000(200)
                .discountPer1000(500)
                .build();

        BrandEntity brand = BrandEntity.builder().id(1).name("Brand 1").build();
        CategoryEntity category = CategoryEntity.builder().id(2).name("Category 1").build();
        ProductImageEntity image = ProductImageEntity.builder().id(3).url("http://example.com/image.jpg").build();

        when(brandRepository.findById(1)).thenReturn(Optional.of(brand));
        when(categoryRepository.findById(2)).thenReturn(Optional.of(category));
        when(productImageRepository.findById(3)).thenReturn(Optional.of(image));

        // When
        ProductEntity productEntity = productMapper.mapFrom(productDto);

        // Then
        assertNotNull(productEntity);
        assertEquals(productDto.getId(), productEntity.getId());
        assertEquals(productDto.getName(), productEntity.getName());
        assertEquals(productDto.getDescription(), productEntity.getDescription());
        assertNotNull(productEntity.getBrand());
        assertEquals(brand.getId(), productEntity.getBrand().getId());
        assertNotNull(productEntity.getCategory());
        assertEquals(category.getId(), productEntity.getCategory().getId());
        assertEquals(1, productEntity.getImages().size());
        assertTrue(productEntity.getImages().contains(image));
        assertEquals(productDto.getAverageScoreOutOf100(), productEntity.getAverageScoreOutOf100());
        assertEquals(productDto.getPriceCents(), productEntity.getPriceCents());
        assertEquals(productDto.getTaxPer1000(), productEntity.getTaxPer1000());
        assertEquals(productDto.getDiscountPer1000(), productEntity.getDiscountPer1000());
    }

    @Test
    void testMapFromProductDtoWithInvalidBrand() {
        // Given
        ProductDto productDto = ProductDto.builder()
                .brandId(999)
                .build();

        when(brandRepository.findById(999)).thenReturn(Optional.empty());

        // When/Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> productMapper.mapFrom(productDto));
        assertEquals("Brand not found with id: 999", exception.getMessage());
    }

    @Test
    void testMapFromProductDtoWithInvalidCategory() {
        // Given
        ProductDto productDto = ProductDto.builder()
                .categoryId(999)
                .build();

        when(categoryRepository.findById(999)).thenReturn(Optional.empty());

        // When/Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> productMapper.mapFrom(productDto));
        assertEquals("Category not found with id: 999", exception.getMessage());
    }

    @Test
    void testMapFromProductDtoWithInvalidImage() {
        // Given
        ProductDto productDto = ProductDto.builder()
                .imagesId(List.of(999))
                .build();

        when(productImageRepository.findById(999)).thenReturn(Optional.empty());

        // When/Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> productMapper.mapFrom(productDto));
        assertEquals("Product image not found with id: 999", exception.getMessage());
    }

    @Test
    void testMapFromProductDtoWithNull() {
        // When
        ProductEntity productEntity = productMapper.mapFrom(null);

        // Then
        assertNull(productEntity);
    }
}
