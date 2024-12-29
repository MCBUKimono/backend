package com.kimono.backend.repositories;


import com.kimono.backend.TestSeed.TestDataUtil;
import com.kimono.backend.domain.entities.BrandEntity;
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
public class BrandRepositoryIntegrationTests {

    private final BrandRepository underTest;

    @Autowired
    public BrandRepositoryIntegrationTests(BrandRepository underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testThatBrandCanBeCreatedAndRecalled() {
        BrandEntity brandEntity = TestDataUtil.createTestBrandA();
        underTest.save(brandEntity);
        Optional<BrandEntity> result = underTest.findById(brandEntity.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(brandEntity);
    }

    @Test
    public void testThatMultipleBrandsCanBeCreatedAndRecalled() {
        BrandEntity brandEntity1 = TestDataUtil.createTestBrandA();
        underTest.save(brandEntity1);
        BrandEntity brandEntity2 = TestDataUtil.createTestBrandB();
        underTest.save(brandEntity2);

        List<BrandEntity> result = underTest.findAll();
        assertThat(result)
                .hasSize(2).
                containsExactly(brandEntity1, brandEntity2);
    }

    @Test
    public void testThatBrandCanBeUpdated() {
        BrandEntity brandEntity1 = TestDataUtil.createTestBrandA();
        underTest.save(brandEntity1);
        brandEntity1.setName("UPDATED");
        underTest.save(brandEntity1);
        Optional<BrandEntity> result = underTest.findById(brandEntity1.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(brandEntity1);

    }

    @Test
    public void testThatBrandCanBeDeleted() {
        BrandEntity brandEntity1 = TestDataUtil.createTestBrandA();
        underTest.save(brandEntity1);
        underTest.deleteById(brandEntity1.getId());
        Optional<BrandEntity> result = underTest.findById(brandEntity1.getId());
        assertThat(result).isEmpty();
    }

    @Test
    public void testThatGetBrandsWithName() {
        BrandEntity brandEntity1 = TestDataUtil.createTestBrandA();
        underTest.save(brandEntity1);
        List<BrandEntity> result = underTest.findByName(brandEntity1.getName());
        assertThat(result).containsExactly(brandEntity1);
    }

}
