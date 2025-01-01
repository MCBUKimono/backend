package com.kimono.backend.repositories;

import com.kimono.backend.TestSeed.TestDataUtil;
import com.kimono.backend.domain.entities.BrandEntity;
import com.kimono.backend.domain.entities.CategoryEntity;
import com.kimono.backend.domain.entities.ProductEntity;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ProductRepositoryIntegrationTests {

    private final ProductRepository underTest;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductRepositoryIntegrationTests(ProductRepository underTest, BrandRepository brandRepository, CategoryRepository categoryRepository) {
        this.underTest = underTest;
        this.brandRepository = brandRepository;
        this.categoryRepository = categoryRepository;
    }

    @Test
    @Transactional
    public void testThatProductCanBeCreatedAndRecalled() {
        BrandEntity brand = TestDataUtil.createTestBrandA();
        brandRepository.save(brand);

        CategoryEntity category = TestDataUtil.createTestCategoryEntityA();
        categoryRepository.save(category);

        ProductEntity product = TestDataUtil.createTestProductA();
        product.setBrand(brand);
        product.setCategory(category);
        underTest.save(product);

        Optional<ProductEntity> result = underTest.findById(product.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(product);
    }

    @Test
    @Transactional
    public void testThatMultipleProductsCanBeCreatedAndRecalled() {
        BrandEntity brand = TestDataUtil.createTestBrandA();
        brandRepository.save(brand);

        CategoryEntity category = TestDataUtil.createTestCategoryEntityA();
        categoryRepository.save(category);

        ProductEntity product1 = TestDataUtil.createTestProductA();
        product1.setBrand(brand);
        product1.setCategory(category);
        underTest.save(product1);

        ProductEntity product2 = TestDataUtil.createTestProductA();
        product2.setName("New Product");
        product2.setBrand(brand);
        product2.setCategory(category);
        underTest.save(product2);

        List<ProductEntity> result = underTest.findAll();
        assertThat(result).hasSize(2).containsExactlyInAnyOrder(product1, product2);
    }

    @Test
    @Transactional
    public void testThatProductCanBeUpdated() {
        BrandEntity brand = TestDataUtil.createTestBrandA();
        brandRepository.save(brand);

        CategoryEntity category = TestDataUtil.createTestCategoryEntityA();
        categoryRepository.save(category);

        ProductEntity product = TestDataUtil.createTestProductA();
        product.setBrand(brand);
        product.setCategory(category);
        underTest.save(product);

        product.setName("Updated Product Name");
        underTest.save(product);

        Optional<ProductEntity> result = underTest.findById(product.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(product);
    }

    @Test
    @Transactional
    public void testThatProductCanBeDeleted() {
        BrandEntity brand = TestDataUtil.createTestBrandA();
        brandRepository.save(brand);

        CategoryEntity category = TestDataUtil.createTestCategoryEntityA();
        categoryRepository.save(category);

        ProductEntity product = TestDataUtil.createTestProductA();
        product.setBrand(brand);
        product.setCategory(category);
        underTest.save(product);

        underTest.deleteById(product.getId());
        Optional<ProductEntity> result = underTest.findById(product.getId());
        assertThat(result).isEmpty();
    }

    @Test
    @Transactional
    public void testThatGetProductsByBrandId() {
        BrandEntity brand = TestDataUtil.createTestBrandA();
        brandRepository.save(brand);

        CategoryEntity category = TestDataUtil.createTestCategoryEntityA();
        categoryRepository.save(category);

        ProductEntity product = TestDataUtil.createTestProductA();
        product.setBrand(brand);
        product.setCategory(category);
        underTest.save(product);

        List<ProductEntity> result = underTest.findByBrandId(brand.getId());
        assertThat(result).hasSize(1).containsExactly(product);
    }
}
