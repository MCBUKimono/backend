package com.kimono.backend.mapper;

import com.kimono.backend.domain.dto.CategoryDto;
import com.kimono.backend.domain.entities.CategoryEntity;
import com.kimono.backend.domain.entities.ProductEntity;
import com.kimono.backend.mappers.impl.CategoryMapperImpl;
import com.kimono.backend.repositories.CategoryRepository;
import com.kimono.backend.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class CategoryMapperImplTest {

    private CategoryMapperImpl categoryMapper;
    private CategoryRepository categoryRepository;
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        categoryRepository = Mockito.mock(CategoryRepository.class);
        productRepository = Mockito.mock(ProductRepository.class);
        categoryMapper = new CategoryMapperImpl(categoryRepository, productRepository);
    }

    @Test
    void testMapToCategoryDto() {
        // Given
        CategoryEntity parentCategory = CategoryEntity.builder().id(1).name("Parent Category").build();
        CategoryEntity subCategory = CategoryEntity.builder().id(3).name("Sub Category").build();
        ProductEntity product = ProductEntity.builder().id(5).name("Product 1").build();

        CategoryEntity categoryEntity = CategoryEntity.builder()
                .id(2)
                .name("Test Category")
                .parentCategory(parentCategory)
                .subCategories(List.of(subCategory))
                .products(List.of(product))
                .build();

        // When
        CategoryDto categoryDto = categoryMapper.mapTo(categoryEntity);

        // Then
        assertNotNull(categoryDto);
        assertEquals(categoryEntity.getId(), categoryDto.getId());
        assertEquals(categoryEntity.getName(), categoryDto.getName());
        assertEquals(parentCategory.getId(), categoryDto.getParentCategoryId());
        assertEquals(1, categoryDto.getSubCategoryIds().size());
        assertTrue(categoryDto.getSubCategoryIds().contains(subCategory.getId()));
        assertEquals(1, categoryDto.getProductIds().size());
        assertTrue(categoryDto.getProductIds().contains(product.getId()));
    }

    @Test
    void testMapToCategoryDtoWithNull() {
        // When
        CategoryDto categoryDto = categoryMapper.mapTo(null);

        // Then
        assertNull(categoryDto);
    }

    @Test
    void testMapFromCategoryDto() {
        // Given
        CategoryDto categoryDto = CategoryDto.builder()
                .id(2)
                .name("Test Category")
                .parentCategoryId(1)
                .productIds(List.of(5))
                .build();

        CategoryEntity parentCategory = CategoryEntity.builder().id(1).name("Parent Category").build();
        ProductEntity product = ProductEntity.builder().id(5).name("Product 1").build();

        when(categoryRepository.findById(1)).thenReturn(Optional.of(parentCategory));
        when(productRepository.findById(5)).thenReturn(Optional.of(product));

        // When
        CategoryEntity categoryEntity = categoryMapper.mapFrom(categoryDto);

        // Then
        assertNotNull(categoryEntity);
        assertEquals(categoryDto.getId(), categoryEntity.getId());
        assertEquals(categoryDto.getName(), categoryEntity.getName());
        assertNotNull(categoryEntity.getParentCategory());
        assertEquals(parentCategory.getId(), categoryEntity.getParentCategory().getId());
        assertEquals(1, categoryEntity.getProducts().size());
        assertTrue(categoryEntity.getProducts().contains(product));
    }

    @Test
    void testMapFromCategoryDtoWithInvalidParentCategory() {
        // Given
        CategoryDto categoryDto = CategoryDto.builder()
                .parentCategoryId(999)
                .build();

        when(categoryRepository.findById(999)).thenReturn(Optional.empty());

        // When/Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> categoryMapper.mapFrom(categoryDto));
        assertEquals("Parent category not found with id: 999", exception.getMessage());
    }

    @Test
    void testMapFromCategoryDtoWithInvalidProduct() {
        // Given
        CategoryDto categoryDto = CategoryDto.builder()
                .productIds(List.of(999))
                .build();

        when(productRepository.findById(999)).thenReturn(Optional.empty());

        // When/Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> categoryMapper.mapFrom(categoryDto));
        assertEquals("Product not found with id: 999", exception.getMessage());
    }

    @Test
    void testMapFromCategoryDtoWithNull() {
        // When
        CategoryEntity categoryEntity = categoryMapper.mapFrom(null);

        // Then
        assertNull(categoryEntity);
    }
}
