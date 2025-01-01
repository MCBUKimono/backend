package com.kimono.backend.repositories;

import com.kimono.backend.TestSeed.TestDataUtil;
import com.kimono.backend.domain.entities.CustomerEntity;
import com.kimono.backend.domain.entities.InvoiceEntity;
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
public class InvoiceRepositoryIntegrationTests {

    private final InvoiceRepository underTest;
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    public InvoiceRepositoryIntegrationTests(InvoiceRepository underTest) {
        this.underTest = underTest;
    }

    @Test
    @Transactional
    public void testThatInvoiceCanBeCreatedAndRecalled() {
        InvoiceEntity invoice = TestDataUtil.createTestInvoiceEntity();
        underTest.save(invoice);
        Optional<InvoiceEntity> result = underTest.findById(invoice.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(invoice);
    }

    @Test
    @Transactional
    public void testThatMultipleInvoicesCanBeCreatedAndRecalled() {
        InvoiceEntity invoice1 = TestDataUtil.createTestInvoiceEntity();
        underTest.save(invoice1);
        InvoiceEntity invoice2 = TestDataUtil.createTestInvoiceEntity();
        invoice2.setInvoiceNumber(123456);
        underTest.save(invoice2);

        List<InvoiceEntity> result = underTest.findAll();
        assertThat(result)
                .hasSize(2)
                .containsExactlyInAnyOrder(invoice1, invoice2);
    }

    @Test
    @Transactional
    public void testThatInvoiceCanBeUpdated() {
        InvoiceEntity invoice = TestDataUtil.createTestInvoiceEntity();
        underTest.save(invoice);
        invoice.setInvoiceAddress("Updated Address");
        underTest.save(invoice);
        Optional<InvoiceEntity> result = underTest.findById(invoice.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(invoice);
    }

    @Test
    @Transactional
    public void testThatInvoiceCanBeDeleted() {
        InvoiceEntity invoice = TestDataUtil.createTestInvoiceEntity();
        underTest.save(invoice);
        underTest.deleteById(invoice.getId());
        Optional<InvoiceEntity> result = underTest.findById(invoice.getId());
        assertThat(result).isEmpty();
    }

    @Test
    @Transactional
    public void testThatGetInvoicesByCustomerId() {
        CustomerEntity customer = TestDataUtil.createTestCustomerEntity();
        customerRepository.save(customer);
        InvoiceEntity invoice = TestDataUtil.createTestInvoiceEntity();
        invoice.setCustomer(customer);
        underTest.save(invoice);
        List<InvoiceEntity> result = underTest.findByCustomerId(invoice.getCustomer().getId());
        assertThat(result).hasSize(1).containsExactly(invoice);
    }
}
