package com.kimono.backend.repositories;

import com.kimono.backend.TestSeed.TestDataUtil;
import com.kimono.backend.domain.entities.CustomerEntity;
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
public class CustomerRepositoryIntegrationTests {

    private final CustomerRepository underTest;

    @Autowired
    public CustomerRepositoryIntegrationTests(CustomerRepository underTest) {
        this.underTest = underTest;
    }

    @Test
    @Transactional
    public void testThatCustomerCanBeCreatedAndRecalled() {
        CustomerEntity customerEntity = TestDataUtil.createTestCustomerEntity();
        underTest.save(customerEntity);
        Optional<CustomerEntity> result = underTest.findById(customerEntity.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(customerEntity);
    }

    @Test
    @Transactional
    public void testThatMultipleCustomersCanBeCreatedAndRecalled() {
        CustomerEntity customer1 = TestDataUtil.createTestCustomerEntity();
        underTest.save(customer1);
        CustomerEntity customer2 = TestDataUtil.createTestCustomerEntity();
        customer2.setName("Jane Doe");
        underTest.save(customer2);

        List<CustomerEntity> result = underTest.findAll();
        assertThat(result)
                .hasSize(2)
                .containsExactlyInAnyOrder(customer1, customer2);
    }

    @Test
    @Transactional
    public void testThatCustomerCanBeUpdated() {
        CustomerEntity customer = TestDataUtil.createTestCustomerEntity();
        underTest.save(customer);
        customer.setName("Updated Name");
        underTest.save(customer);
        Optional<CustomerEntity> result = underTest.findById(customer.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(customer);
    }

    @Test
    @Transactional
    public void testThatCustomerCanBeDeleted() {
        CustomerEntity customer = TestDataUtil.createTestCustomerEntity();
        underTest.save(customer);
        underTest.deleteById(customer.getId());
        Optional<CustomerEntity> result = underTest.findById(customer.getId());
        assertThat(result).isEmpty();
    }

    @Test
    @Transactional
    public void testThatGetCustomerByName() {
        CustomerEntity customer = TestDataUtil.createTestCustomerEntity();
        underTest.save(customer);
        List<CustomerEntity> result = underTest.findByName(customer.getName());
        assertThat(result).hasSize(1).containsExactly(customer);
    }
}
