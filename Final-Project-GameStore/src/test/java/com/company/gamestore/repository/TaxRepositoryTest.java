package com.company.gamestore.repository;


import com.company.gamestore.model.Tax;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class TaxRepositoryTest {


    @Autowired
    TaxRepository taxRepository;

    @Test
    public void shouldGetTaxByState(){

        Tax tax = new Tax();
        tax.setState("FL");
        tax.setRate(BigDecimal.valueOf(0.06));

        List<Tax> taxR = taxRepository.findByState(tax.getState());
        assertTrue(taxR.contains(tax));
    }
}
