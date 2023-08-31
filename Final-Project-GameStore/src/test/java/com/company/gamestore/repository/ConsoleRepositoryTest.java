package com.company.gamestore.repository;

import static org.junit.jupiter.api.Assertions.*;
import com.company.gamestore.model.Console;
import com.company.gamestore.model.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;



@SpringBootTest
class ConsoleRepositoryTest {

    @Autowired
    ConsoleRepository ConRepo;

    Console console;


    @BeforeEach
    public void setUp() throws Exception{
        ConRepo.deleteAll();
        console = new Console();
        console.setModel("PlayStation 5");
        console.setManufacturer("Sony");
        console.setMemoryAmount("825GB SSD");
        console.setProcessor("Custom AMD Zen 2");
        console.setPrice(499.99);
        console.setQuantity(100);

    }



    //Creating Console test
    @Test
    public void shouldCreateConsole(){
        console = ConRepo.save(console);

        Optional<Console> console1 = ConRepo.findById(console.getConsoleId());
        assertEquals(console1.get(), console);
    }


    // Rad console by ID test

    @Test
    public void shouldGetConsoleById(){
        ConRepo.save(console);
        Optional<Console> console1 = ConRepo.findById(console.getConsoleId());
        assertEquals(console1.get(), console);
    }




    //Read All console Test


    //Update Console Test
    @Test
    public void shouldUpdateGame(){
        console = ConRepo.save(console);

        console.setModel("PlayStation 5");
        console.setManufacturer("Sony");
        console.setMemoryAmount("825GB SSD");

// Update in DB
        ConRepo.save(console);
        Optional<Console> console1 = ConRepo.findById(console.getConsoleId());
        assertEquals(console1.get(), console);
    }


    //Delete Console Test
    @Test
    public void shouldDeleteAConsole(){
        console = ConRepo.save(console);
        Optional<Console> console1 = ConRepo.findById(console.getConsoleId());
        assertEquals(console1.get(), console);

        ConRepo.deleteById(console.getConsoleId());

        console1 = ConRepo.findById(console.getConsoleId());
        assertFalse(console1.isPresent());
    }



    //By Manufacturer Console Test
    @Test
    public void shouldGetGameByStudio(){
        ConRepo.save(console);
        console.setConsoleId(15);
        ConRepo.save(console);

        List<Console> consoles = ConRepo.findByManufacturer(console.getManufacturer());
        assertEquals(consoles.size(), 2);
    }

}