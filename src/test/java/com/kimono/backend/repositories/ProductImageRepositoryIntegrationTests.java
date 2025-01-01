package com.kimono.backend.repositories;

import com.kimono.backend.TestSeed.TestDataUtil;
import com.kimono.backend.domain.entities.ProductEntity;
import com.kimono.backend.domain.entities.ProductImageEntity;
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
public class ProductImageRepositoryIntegrationTests {

    private final ProductImageRepository underTest;
    private final ProductRepository productRepository;

    @Autowired
    public ProductImageRepositoryIntegrationTests(ProductImageRepository underTest, ProductRepository productRepository) {
        this.underTest = underTest;
        this.productRepository = productRepository;
    }

    @Test
    @Transactional
    public void testThatProductImageCanBeCreatedAndRecalled() {
        ProductEntity product = TestDataUtil.createTestProductA();
        productRepository.save(product);

        ProductImageEntity image = TestDataUtil.createTestProductImageEntityA();
        image.setProduct(product);
        underTest.save(image);

        Optional<ProductImageEntity> result = underTest.findById(image.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(image);
    }

    @Test
    @Transactional
    public void testThatMultipleProductImagesCanBeCreatedAndRecalled() {
        ProductEntity product = TestDataUtil.createTestProductA();
        productRepository.save(product);

        ProductImageEntity image1 = TestDataUtil.createTestProductImageEntityA();
        image1.setProduct(product);
        underTest.save(image1);

        ProductImageEntity image2 = TestDataUtil.createTestProductImageEntityB();
        image2.setProduct(product);
        underTest.save(image2);

        List<ProductImageEntity> result = underTest.findAll();
        assertThat(result).hasSize(2).containsExactlyInAnyOrder(image1, image2);
    }

    @Test
    @Transactional
    public void testThatProductImageCanBeUpdated() {
        ProductEntity product = TestDataUtil.createTestProductA();
        productRepository.save(product);

        ProductImageEntity image = TestDataUtil.createTestProductImageEntityA();
        image.setProduct(product);
        underTest.save(image);

        image.setUrl("http://updated-url.com");
        underTest.save(image);

        Optional<ProductImageEntity> result = underTest.findById(image.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(image);
    }

    @Test
    @Transactional
    public void testThatProductImageCanBeDeleted() {
        ProductEntity product = TestDataUtil.createTestProductA();
        productRepository.save(product);

        ProductImageEntity image = TestDataUtil.createTestProductImageEntityA();
        image.setProduct(product);
        underTest.save(image);

        underTest.deleteById(image.getId());
        Optional<ProductImageEntity> result = underTest.findById(image.getId());
        assertThat(result).isEmpty();
    }

    @Test
    @Transactional
    public void testThatGetProductImagesByProductId() {
        ProductEntity product = TestDataUtil.createTestProductA();
        productRepository.save(product);

        ProductImageEntity image = TestDataUtil.createTestProductImageEntityA();
        image.setProduct(product);
        underTest.save(image);

        List<ProductImageEntity> result = underTest.findByProductId(product.getId());
        assertThat(result).hasSize(1).containsExactly(image);
    }
}
