package com.company.gamestore.repository;

import com.company.gamestore.model.Invoice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class InvoiceRepositoryTest {

    @Autowired
    InvoiceRepository invoiceRepository;

    Invoice invoice;

    @BeforeEach
    public void setUp(){
        invoiceRepository.deleteAll();
        invoice = new Invoice();
        invoice.setName("Customer name");
        invoice.setStreet("1111 Customer street");
        invoice.setCity("Redwood");
        invoice.setState("California");
        invoice.setZipcode("94065");
        invoice.setItemType("Console");
        invoice.setItemId(1412);
        invoice.setUnitPrice(BigDecimal.valueOf(49.99));
        invoice.setQuantity(1);
        invoice.setSubtotal(BigDecimal.valueOf(49.99));
        invoice.setTax(BigDecimal.valueOf(2.99));
        invoice.setProcessingFee(BigDecimal.valueOf(1.99));
        invoice.setTotal(BigDecimal.valueOf(54.97));
        invoiceRepository.save(invoice);

    }


    @Test
    public void shouldGetInvoiceById(){
        Optional<Invoice> invoice1 = invoiceRepository.findById(invoice.getInvoiceId());
        assertEquals(invoice1.get(), invoice);
    }

    @Test
    public void shouldGetInvoiceByCustomerName(){
        List<Invoice> invoices = invoiceRepository.findByName(invoice.getName());
        assertTrue(invoices.contains(invoice));
    }

    @Test
    public void shouldAddNewInvoice(){
        Invoice invoice1 = new Invoice();
        invoice1.setName("Customer2 name");
        invoice1.setStreet("2222 Customer2 street");
        invoice1.setCity("Los Gatos");
        invoice1.setState("California");
        invoice1.setZipcode("95032");
        invoice1.setItemType("Console");
        invoice1.setItemId(1412);
        invoice1.setUnitPrice(BigDecimal.valueOf(49.99));
        invoice1.setQuantity(2);
        invoice1.setSubtotal(BigDecimal.valueOf(99.98));
        invoice1.setTax(BigDecimal.valueOf(5.99));
        invoice1.setProcessingFee(BigDecimal.valueOf(1.99));
        invoice1.setTotal(BigDecimal.valueOf(107.96));
        invoiceRepository.save(invoice1);
        List<Invoice> invoices = invoiceRepository.findAll();
        assertTrue(invoices.contains(invoice1));
        Optional<Invoice> invoice2 = invoiceRepository.findById(invoice1.getInvoiceId());
        assertEquals(invoice2.get(), invoice1);
    }

    @Test
    public void shouldUpdateInvoice(){
        invoice.setProcessingFee(BigDecimal.valueOf(2.99));
        invoice.setTotal(BigDecimal.valueOf(55.97));
        invoiceRepository.save(invoice);
        Optional<Invoice> invoice1 = invoiceRepository.findById(invoice.getInvoiceId());
        assertEquals(invoice1.get(), invoice);
        assertEquals(invoice1.get().getProcessingFee(), BigDecimal.valueOf(2.99));
        assertEquals(invoice1.get().getTotal(), BigDecimal.valueOf(55.97));
    }

    @Test
    public void shouldDeleteInvoice(){
        invoiceRepository.deleteById(invoice.getInvoiceId());
        Optional<Invoice> deletedInvoice = invoiceRepository.findById(invoice.getInvoiceId());
        assertFalse(deletedInvoice.isPresent());
    }
}
