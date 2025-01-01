package com.kimono.backend.repositories;

import com.kimono.backend.TestSeed.TestDataUtil;
import com.kimono.backend.domain.entities.InvoiceEntity;
import com.kimono.backend.domain.entities.ProductEntity;
import com.kimono.backend.domain.entities.ProductSnapshotEntity;
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
public class ProductSnapshotRepositoryIntegrationTests {

    private final ProductSnapshotRepository underTest;
    private final ProductRepository productRepository;
    private final InvoiceRepository invoiceRepository;

    @Autowired
    public ProductSnapshotRepositoryIntegrationTests(ProductSnapshotRepository underTest,
                                                     ProductRepository productRepository,
                                                     InvoiceRepository invoiceRepository) {
        this.underTest = underTest;
        this.productRepository = productRepository;
        this.invoiceRepository = invoiceRepository;
    }

    @Test
    @Transactional
    public void testThatProductSnapshotCanBeCreatedAndRecalled() {
        ProductEntity product = TestDataUtil.createTestProductA();
        productRepository.save(product);

        InvoiceEntity invoice = TestDataUtil.createTestInvoiceEntity();
        invoiceRepository.save(invoice);

        ProductSnapshotEntity snapshot = TestDataUtil.createTestProductSnapshotEntity();
        snapshot.setProduct(product);
        snapshot.setInvoice(invoice);
        underTest.save(snapshot);

        Optional<ProductSnapshotEntity> result = underTest.findById(snapshot.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(snapshot);
    }

    @Test
    @Transactional
    public void testThatMultipleProductSnapshotsCanBeCreatedAndRecalled() {
        ProductEntity product = TestDataUtil.createTestProductA();
        productRepository.save(product);

        InvoiceEntity invoice = TestDataUtil.createTestInvoiceEntity();
        invoiceRepository.save(invoice);

        ProductSnapshotEntity snapshot1 = TestDataUtil.createTestProductSnapshotEntity();
        snapshot1.setProduct(product);
        snapshot1.setInvoice(invoice);
        underTest.save(snapshot1);

        ProductSnapshotEntity snapshot2 = TestDataUtil.createTestProductSnapshotEntity();
        snapshot2.setProduct(product);
        snapshot2.setInvoice(invoice);
        underTest.save(snapshot2);

        List<ProductSnapshotEntity> result = underTest.findAll();
        assertThat(result).hasSize(2).containsExactlyInAnyOrder(snapshot1, snapshot2);
    }

    @Test
    @Transactional
    public void testThatProductSnapshotCanBeUpdated() {
        ProductEntity product = TestDataUtil.createTestProductA();
        productRepository.save(product);

        InvoiceEntity invoice = TestDataUtil.createTestInvoiceEntity();
        invoiceRepository.save(invoice);

        ProductSnapshotEntity snapshot = TestDataUtil.createTestProductSnapshotEntity();
        snapshot.setProduct(product);
        snapshot.setInvoice(invoice);
        underTest.save(snapshot);

        snapshot.setPriceCents(20000);
        underTest.save(snapshot);

        Optional<ProductSnapshotEntity> result = underTest.findById(snapshot.getId());
        assertThat(result).isPresent();
        assertThat(result.get().getPriceCents()).isEqualTo(20000);
    }

    @Test
    @Transactional
    public void testThatProductSnapshotCanBeDeleted() {
        ProductEntity product = TestDataUtil.createTestProductA();
        productRepository.save(product);

        InvoiceEntity invoice = TestDataUtil.createTestInvoiceEntity();
        invoiceRepository.save(invoice);

        ProductSnapshotEntity snapshot = TestDataUtil.createTestProductSnapshotEntity();
        snapshot.setProduct(product);
        snapshot.setInvoice(invoice);
        underTest.save(snapshot);

        underTest.deleteById(snapshot.getId());
        Optional<ProductSnapshotEntity> result = underTest.findById(snapshot.getId());
        assertThat(result).isEmpty();
    }

    @Test
    @Transactional
    public void testThatGetProductSnapshotsByProductId() {
        ProductEntity product = TestDataUtil.createTestProductA();
        productRepository.save(product);

        InvoiceEntity invoice = TestDataUtil.createTestInvoiceEntity();
        invoiceRepository.save(invoice);

        ProductSnapshotEntity snapshot = TestDataUtil.createTestProductSnapshotEntity();
        snapshot.setProduct(product);
        snapshot.setInvoice(invoice);
        underTest.save(snapshot);

        List<ProductSnapshotEntity> result = underTest.findByProductId(product.getId());
        assertThat(result).hasSize(1).containsExactly(snapshot);
    }

    @Test
    @Transactional
    public void testThatGetProductSnapshotsByInvoiceId() {
        ProductEntity product = TestDataUtil.createTestProductA();
        productRepository.save(product);

        InvoiceEntity invoice = TestDataUtil.createTestInvoiceEntity();
        invoiceRepository.save(invoice);

        ProductSnapshotEntity snapshot = TestDataUtil.createTestProductSnapshotEntity();
        snapshot.setProduct(product);
        snapshot.setInvoice(invoice);
        underTest.save(snapshot);

        List<ProductSnapshotEntity> result = underTest.findByInvoiceId(invoice.getId());
        assertThat(result).hasSize(1).containsExactly(snapshot);
    }
}
