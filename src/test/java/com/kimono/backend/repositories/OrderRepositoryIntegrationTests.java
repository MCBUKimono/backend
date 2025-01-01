package com.kimono.backend.repositories;

import com.kimono.backend.TestSeed.TestDataUtil;
import com.kimono.backend.domain.entities.CustomerEntity;
import com.kimono.backend.domain.entities.OrderEntity;
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
public class OrderRepositoryIntegrationTests {

    private final OrderRepository underTest;
    private final CustomerRepository customerRepository;

    @Autowired
    public OrderRepositoryIntegrationTests(OrderRepository underTest, CustomerRepository customerRepository) {
        this.underTest = underTest;
        this.customerRepository = customerRepository;
    }

    @Test
    @Transactional
    public void testThatOrderCanBeCreatedAndRecalled() {
        CustomerEntity customer = TestDataUtil.createTestCustomerEntity();
        customerRepository.save(customer);

        OrderEntity order = TestDataUtil.createTestOrderEntity();
        order.setCustomer(customer);
        underTest.save(order);

        Optional<OrderEntity> result = underTest.findById(order.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(order);
    }

    @Test
    @Transactional
    public void testThatMultipleOrdersCanBeCreatedAndRecalled() {
        CustomerEntity customer = TestDataUtil.createTestCustomerEntity();
        customerRepository.save(customer);

        OrderEntity order1 = TestDataUtil.createTestOrderEntity();
        order1.setCustomer(customer);
        underTest.save(order1);

        OrderEntity order2 = TestDataUtil.createTestOrderEntity();
        order2.setCustomer(customer);
        order2.setShippingName("Jane Doe");
        underTest.save(order2);

        List<OrderEntity> result = underTest.findAll();
        assertThat(result).hasSize(2).containsExactlyInAnyOrder(order1, order2);
    }

    @Test
    @Transactional
    public void testThatOrderCanBeUpdated() {
        CustomerEntity customer = TestDataUtil.createTestCustomerEntity();
        customerRepository.save(customer);

        OrderEntity order = TestDataUtil.createTestOrderEntity();
        order.setCustomer(customer);
        underTest.save(order);

        order.setShippingName("Updated Name");
        underTest.save(order);

        Optional<OrderEntity> result = underTest.findById(order.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(order);
    }

    @Test
    @Transactional
    public void testThatOrderCanBeDeleted() {
        CustomerEntity customer = TestDataUtil.createTestCustomerEntity();
        customerRepository.save(customer);

        OrderEntity order = TestDataUtil.createTestOrderEntity();
        order.setCustomer(customer);
        underTest.save(order);

        underTest.deleteById(order.getId());
        Optional<OrderEntity> result = underTest.findById(order.getId());
        assertThat(result).isEmpty();
    }

    @Test
    @Transactional
    public void testThatGetOrdersByCustomerId() {
        CustomerEntity customer = TestDataUtil.createTestCustomerEntity();
        customerRepository.save(customer);

        OrderEntity order = TestDataUtil.createTestOrderEntity();
        order.setCustomer(customer);
        underTest.save(order);

        List<OrderEntity> result = underTest.findByCustomerId(customer.getId());
        assertThat(result).hasSize(1).containsExactly(order);
    }
}
