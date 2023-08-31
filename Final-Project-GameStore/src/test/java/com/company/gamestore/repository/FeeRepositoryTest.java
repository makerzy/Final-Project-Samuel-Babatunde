package com.company.gamestore.repository;


import com.company.gamestore.model.Fee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class FeeRepositoryTest {

    @Autowired
    FeeRepository feeRepository;

    @Test
    public void shouldGetFeeByProductType(){
        Fee fee = new Fee();
        fee.setFee(BigDecimal.valueOf(14.99));
        fee.setProductType("Console");

        List<Fee> fees = feeRepository.findByProductType("Console");
        assertTrue(fees.contains(fee));

    }
}
