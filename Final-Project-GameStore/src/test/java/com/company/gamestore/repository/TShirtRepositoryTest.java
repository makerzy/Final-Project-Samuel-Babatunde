package com.company.gamestore.repository;


import com.company.gamestore.model.TShirt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TShirtRepositoryTest {
    @Autowired
    TShirtRepository tShirtRepository;

    private TShirt tShirt;

    @BeforeEach
    public void setUp(){
        tShirtRepository.deleteAll();
        tShirt = new TShirt();
        tShirt.setSize("M");
        tShirt.setColor("Red");
        tShirt.setDescription("TShirt description");
        tShirt.setPrice(BigDecimal.valueOf(19.99));
        tShirt.setQuantity(20);
        tShirtRepository.save(tShirt);
    }

    @Test
    public void shouldGetTShirtById(){
        Optional<TShirt> tShirt1 = tShirtRepository.findById(tShirt.getTshirtId());
        assertEquals(tShirt1.get(), tShirt);
    }

    @Test
    public void shouldGetTShirtByColor(){
        List<TShirt> tShirts = tShirtRepository.findByColor(tShirt.getColor());
        assertTrue(tShirts.contains(tShirt));
    }

    @Test
    public void shouldGetTShirtBySize(){
        List<TShirt> tShirts = tShirtRepository.findBySize(tShirt.getSize());
        assertTrue(tShirts.contains(tShirt));
    }

    @Test
    public void shouldAddNewTShirt(){
        TShirt tShirt1 = new TShirt();
        tShirt1.setQuantity(23);
        tShirt1.setSize("L");
        tShirt1.setColor("Green");
        tShirt1.setDescription("Description for a green T-shirt");
        tShirt1.setPrice(BigDecimal.valueOf(24.99));
        tShirt1 = tShirtRepository.save(tShirt1);
        List<TShirt> tShirts = tShirtRepository.findAll();
        assertTrue(tShirts.contains(tShirt1));
        assertEquals(tShirts.size(), 2);
    }


    @Test
    public void shouldUpdateTShirt(){
        tShirt.setPrice(BigDecimal.valueOf(14.99));
        tShirtRepository.save(tShirt);
        Optional<TShirt> tShirt1 = tShirtRepository.findById(tShirt.getTshirtId());
        assertEquals(tShirt1.get().getPrice(), BigDecimal.valueOf(14.99));
        assertEquals(tShirt1.get(), tShirt);
    }

    @Test
    public void shouldDeleteTShirt(){
        tShirtRepository.deleteById(tShirt.getTshirtId());
        Optional<TShirt> deletedTShirt = tShirtRepository.findById(tShirt.getTshirtId());
        assertFalse(deletedTShirt.isPresent());
    }

}
