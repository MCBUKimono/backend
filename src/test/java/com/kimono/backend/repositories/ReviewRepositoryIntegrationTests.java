package com.kimono.backend.repositories;

import com.kimono.backend.TestSeed.TestDataUtil;
import com.kimono.backend.domain.entities.CustomerEntity;
import com.kimono.backend.domain.entities.ProductEntity;
import com.kimono.backend.domain.entities.ReviewEntity;
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
public class ReviewRepositoryIntegrationTests {

    private final ReviewRepository underTest;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public ReviewRepositoryIntegrationTests(ReviewRepository underTest, ProductRepository productRepository, CustomerRepository customerRepository) {
        this.underTest = underTest;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
    }

    @Test
    @Transactional
    public void testThatReviewCanBeCreatedAndRecalled() {
        ProductEntity product = TestDataUtil.createTestProductA();
        productRepository.save(product);

        CustomerEntity customer = TestDataUtil.createTestCustomerEntity();
        customerRepository.save(customer);

        ReviewEntity review = TestDataUtil.createTestReviewEntity();
        review.setProduct(product);
        review.setCustomer(customer);
        underTest.save(review);

        Optional<ReviewEntity> result = underTest.findById(review.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(review);
    }

    @Test
    @Transactional
    public void testThatMultipleReviewsCanBeCreatedAndRecalled() {
        ProductEntity product = TestDataUtil.createTestProductA();
        productRepository.save(product);

        CustomerEntity customer = TestDataUtil.createTestCustomerEntity();
        customerRepository.save(customer);

        ReviewEntity review1 = TestDataUtil.createTestReviewEntity();
        review1.setProduct(product);
        review1.setCustomer(customer);
        underTest.save(review1);

        ReviewEntity review2 = TestDataUtil.createTestReviewEntity();
        review2.setProduct(product);
        review2.setCustomer(customer);
        review2.setText("Another review text");
        underTest.save(review2);

        List<ReviewEntity> result = underTest.findAll();
        assertThat(result).hasSize(2).containsExactlyInAnyOrder(review1, review2);
    }

    @Test
    @Transactional
    public void testThatReviewCanBeUpdated() {
        ProductEntity product = TestDataUtil.createTestProductA();
        productRepository.save(product);

        CustomerEntity customer = TestDataUtil.createTestCustomerEntity();
        customerRepository.save(customer);

        ReviewEntity review = TestDataUtil.createTestReviewEntity();
        review.setProduct(product);
        review.setCustomer(customer);
        underTest.save(review);

        review.setText("Updated review text");
        underTest.save(review);

        Optional<ReviewEntity> result = underTest.findById(review.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(review);
    }

    @Test
    @Transactional
    public void testThatReviewCanBeDeleted() {
        ProductEntity product = TestDataUtil.createTestProductA();
        productRepository.save(product);

        CustomerEntity customer = TestDataUtil.createTestCustomerEntity();
        customerRepository.save(customer);

        ReviewEntity review = TestDataUtil.createTestReviewEntity();
        review.setProduct(product);
        review.setCustomer(customer);
        underTest.save(review);

        underTest.deleteById(review.getId());
        Optional<ReviewEntity> result = underTest.findById(review.getId());
        assertThat(result).isEmpty();
    }

    @Test
    @Transactional
    public void testThatGetReviewsByProductId() {
        ProductEntity product = TestDataUtil.createTestProductA();
        productRepository.save(product);

        CustomerEntity customer = TestDataUtil.createTestCustomerEntity();
        customerRepository.save(customer);

        ReviewEntity review = TestDataUtil.createTestReviewEntity();
        review.setProduct(product);
        review.setCustomer(customer);
        underTest.save(review);

        List<ReviewEntity> result = underTest.findByProductId(product.getId());
        assertThat(result).hasSize(1).containsExactly(review);
    }

    @Test
    @Transactional
    public void testThatGetReviewsByCustomerId() {
        ProductEntity product = TestDataUtil.createTestProductA();
        productRepository.save(product);

        CustomerEntity customer = TestDataUtil.createTestCustomerEntity();
        customerRepository.save(customer);

        ReviewEntity review = TestDataUtil.createTestReviewEntity();
        review.setProduct(product);
        review.setCustomer(customer);
        underTest.save(review);

        List<ReviewEntity> result = underTest.findByCustomerId(customer.getId());
        assertThat(result).hasSize(1).containsExactly(review);
    }
}
