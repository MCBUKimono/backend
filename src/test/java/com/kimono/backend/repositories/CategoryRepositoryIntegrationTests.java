package com.kimono.backend.repositories;

import com.kimono.backend.TestSeed.TestDataUtil;
import com.kimono.backend.domain.entities.CategoryEntity;
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
public class CategoryRepositoryIntegrationTests {

    private final CategoryRepository underTest;

    @Autowired
    public CategoryRepositoryIntegrationTests(CategoryRepository underTest) {
        this.underTest = underTest;
    }

    @Test
    @Transactional
    public void testThatCategoryCanBeCreatedAndRecalled() {
        CategoryEntity categoryEntity = TestDataUtil.createTestCategoryEntityA();
        underTest.save(categoryEntity);
        Optional<CategoryEntity> result = underTest.findById(categoryEntity.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(categoryEntity);
    }

    @Test
    @Transactional
    public void testThatMultipleCategoriesCanBeCreatedAndRecalled() {
        CategoryEntity category1 = TestDataUtil.createTestCategoryEntityA();
        underTest.save(category1);
        CategoryEntity category2 = TestDataUtil.createTestCategoryEntityB();
        underTest.save(category2);
        CategoryEntity category3 = TestDataUtil.createTestCategoryEntityC();
        underTest.save(category3);

        List<CategoryEntity> result = underTest.findAll();
        assertThat(result)
                .hasSize(3)
                .containsExactly(category1, category2, category3);
    }

    @Test
    @Transactional
    public void testThatCategoryCanBeUpdated() {
        CategoryEntity category = TestDataUtil.createTestCategoryEntityA();
        underTest.save(category);
        category.setName("UPDATED");
        underTest.save(category);
        Optional<CategoryEntity> result = underTest.findById(category.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(category);
    }

    @Test
    @Transactional
    public void testThatCategoryCanBeDeleted() {
        CategoryEntity category = TestDataUtil.createTestCategoryEntityA();
        underTest.save(category);
        underTest.deleteById(category.getId());
        Optional<CategoryEntity> result = underTest.findById(category.getId());
        assertThat(result).isEmpty();
    }

    @Test
    @Transactional
    public void testThatGetCategoriesByName() {
        CategoryEntity category = TestDataUtil.createTestCategoryEntityA();
        underTest.save(category);
        Optional<CategoryEntity> result = underTest.findByName(category.getName());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(category);
    }
}
