package com.kimono.backend.repositories;

import com.kimono.backend.TestSeed.TestData;
import com.kimono.backend.domain.entities.CategoryEntity;
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
    public void testThatCategoryCanBeCreatedAndRecalled() {
        CategoryEntity categoryEntity = TestData.createCategoryEntity1();
        underTest.save(categoryEntity);
        Optional<CategoryEntity> result = underTest.findById(categoryEntity.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(categoryEntity);
    }

    @Test
    public void testThatMultipleCategoriesCanBeCreatedAndRecalled() {
        CategoryEntity category1 = TestData.createCategoryEntity1();
        underTest.save(category1);
        CategoryEntity category2 = TestData.createCategoryEntity2();
        underTest.save(category2);
        CategoryEntity category3 = TestData.createCategoryEntity3();
        underTest.save(category3);

        List<CategoryEntity> result = underTest.findAll();
        assertThat(result)
                .hasSize(3)
                .containsExactly(category1, category2, category3);
    }

    @Test
    public void testThatCategoryCanBeUpdated() {
        CategoryEntity category = TestData.createCategoryEntity1();
        underTest.save(category);
        category.setName("UPDATED");
        underTest.save(category);
        Optional<CategoryEntity> result = underTest.findById(category.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(category);
    }

    @Test
    public void testThatCategoryCanBeDeleted() {
        CategoryEntity category = TestData.createCategoryEntity1();
        underTest.save(category);
        underTest.deleteById(category.getId());
        Optional<CategoryEntity> result = underTest.findById(category.getId());
        assertThat(result).isEmpty();
    }

    @Test
    public void testThatGetCategoriesByName() {
        CategoryEntity category = TestData.createCategoryEntity1();
        underTest.save(category);
        Optional<CategoryEntity> result = underTest.findByName("Electronics");
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(category);
    }
}
