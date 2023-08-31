package com.company.gamestore.controller;


import com.company.gamestore.model.Console;
import com.company.gamestore.model.Invoice;
import com.company.gamestore.model.TShirt;
import com.company.gamestore.repository.*;
import com.company.gamestore.service.ServiceLayer;
import com.company.gamestore.viewmodel.InvoiceViewModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(InvoiceController.class)
@AutoConfigureMockMvc(addFilters = false)
public class InvoiceControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private final ObjectMapper mapper = new ObjectMapper();
    @MockBean
    private InvoiceRepository invoiceRepository;

    @MockBean
    private TaxRepository taxRepository;

    @MockBean
    private FeeRepository feeRepository;

    @MockBean
    private GameRepository gameRepository;
    @MockBean
    private TShirtRepository tShirtRepository;

    @MockBean
    private ConsoleRepository consoleRepository;

    @MockBean
    private ServiceLayer serviceLayer;

    private InvoiceViewModel model;

    private Invoice invoice;




    @BeforeEach
    public void setUp() {


        model = new InvoiceViewModel();
        model.setInvoiceId(1);
        model.setName("Customer name");
        model.setStreet("1111 Customer street");
        model.setCity("Redwood");
        model.setState("California");
        model.setZipcode("94065");
        model.setItemType("Console");
        model.setItemId(1);
        model.setQuantity(1);

        invoice = new Invoice();
        invoice.setInvoiceId(1);
        invoice.setName("Customer name");
        invoice.setStreet("1111 Customer street");
        invoice.setCity("Redwood");
        invoice.setState("California");
        invoice.setZipcode("94065");
        invoice.setItemType("Console");
        invoice.setItemId(1);
        invoice.setQuantity(1);
        invoice.setUnitPrice(BigDecimal.valueOf(499.99));
        invoice.setSubtotal(BigDecimal.valueOf(999.98));
        invoice.setTax(BigDecimal.valueOf(69.99));
        invoice.setProcessingFee(BigDecimal.valueOf(14.99));
        invoice.setTotal(BigDecimal.valueOf(1084.97));

    }

    @Test
    public void shouldCreateNewInvoice() throws Exception {

        when(serviceLayer.saveInvoice(any(InvoiceViewModel.class))).thenReturn(invoice);
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/invoices")
                                .content(mapper.writeValueAsString(model)) // input
                                .contentType(MediaType.APPLICATION_JSON)

                )

                .andExpect(status().isCreated())
                .andExpect(content().json(mapper.writeValueAsString(invoice)))
                .andDo(print())
                .andReturn();
    }

    @Test
    public void shouldGetAllInvoices() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/invoices"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andReturn();
    }

    @Test
    public void shouldGetInvoiceById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/invoices/{id}", invoice.getInvoiceId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void shouldGetInvoiceByCustomerName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/invoices/customers/{name}", invoice.getName()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }


    @Test
    public void shouldUpdateInvoice() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders
                        .put("/invoices/{id}", invoice.getInvoiceId())
                        .content(mapper.writeValueAsString(invoice))
                        .contentType(MediaType.APPLICATION_JSON)

        ).andExpect(status().isNoContent());
    }

    @Test
    public void shouldDeleteInvoice() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/invoices/{id}", invoice.getInvoiceId())

        ).andExpect(status().isNoContent());
    }


}
