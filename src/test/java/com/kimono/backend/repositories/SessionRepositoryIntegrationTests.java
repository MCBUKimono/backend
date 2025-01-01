package com.kimono.backend.repositories;

import com.kimono.backend.TestSeed.TestDataUtil;
import com.kimono.backend.domain.entities.CustomerEntity;
import com.kimono.backend.domain.entities.SessionEntity;
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
public class SessionRepositoryIntegrationTests {

    private final SessionRepository underTest;
    private final CustomerRepository customerRepository;

    @Autowired
    public SessionRepositoryIntegrationTests(SessionRepository underTest, CustomerRepository customerRepository) {
        this.underTest = underTest;
        this.customerRepository = customerRepository;
    }

    @Test
    @Transactional
    public void testThatSessionCanBeCreatedAndRecalled() {
        CustomerEntity customer = TestDataUtil.createTestCustomerEntity();
        customerRepository.save(customer);

        SessionEntity session = TestDataUtil.createTestSessionEntity();
        session.setCustomer(customer);
        underTest.save(session);

        Optional<SessionEntity> result = underTest.findById(session.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(session);
    }

    @Test
    @Transactional
    public void testThatMultipleSessionsCanBeCreatedAndRecalled() {
        CustomerEntity customer = TestDataUtil.createTestCustomerEntity();
        customerRepository.save(customer);

        SessionEntity session1 = TestDataUtil.createTestSessionEntity();
        session1.setCustomer(customer);
        underTest.save(session1);

        SessionEntity session2 = TestDataUtil.createTestSessionEntity();
        session2.setCustomer(customer);
        session2.setToken("another-token");
        underTest.save(session2);

        List<SessionEntity> result = underTest.findAll();
        assertThat(result).hasSize(2).containsExactlyInAnyOrder(session1, session2);
    }

    @Test
    @Transactional
    public void testThatSessionCanBeUpdated() {
        CustomerEntity customer = TestDataUtil.createTestCustomerEntity();
        customerRepository.save(customer);

        SessionEntity session = TestDataUtil.createTestSessionEntity();
        session.setCustomer(customer);
        underTest.save(session);

        session.setToken("updated-token");
        underTest.save(session);

        Optional<SessionEntity> result = underTest.findById(session.getId());
        assertThat(result).isPresent();
        assertThat(result.get().getToken()).isEqualTo("updated-token");
    }

    @Test
    @Transactional
    public void testThatSessionCanBeDeleted() {
        CustomerEntity customer = TestDataUtil.createTestCustomerEntity();
        customerRepository.save(customer);

        SessionEntity session = TestDataUtil.createTestSessionEntity();
        session.setCustomer(customer);
        underTest.save(session);

        underTest.deleteById(session.getId());
        Optional<SessionEntity> result = underTest.findById(session.getId());
        assertThat(result).isEmpty();
    }

    @Test
    @Transactional
    public void testThatGetSessionsByCustomerId() {
        CustomerEntity customer = TestDataUtil.createTestCustomerEntity();
        customerRepository.save(customer);

        SessionEntity session = TestDataUtil.createTestSessionEntity();
        session.setCustomer(customer);
        underTest.save(session);

        List<SessionEntity> result = underTest.findByCustomerId(customer.getId());
        assertThat(result).hasSize(1).containsExactly(session);
    }
}
