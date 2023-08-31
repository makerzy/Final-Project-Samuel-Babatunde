package com.company.gamestore.controller;

import com.company.gamestore.model.Console;
import com.company.gamestore.model.Game;
import com.company.gamestore.model.TShirt;
import com.company.gamestore.repository.ConsoleRepository;
import com.company.gamestore.repository.TShirtRepository;
import com.company.gamestore.service.ServiceLayer;
import com.company.gamestore.service.ServiceLayerTest;
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
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(ConsoleController.class)
class ConsoleControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private final ObjectMapper mapper = new ObjectMapper();
    @MockBean
    private ConsoleRepository consoleRepository;

    @MockBean
    private ServiceLayer serviceLayer;
    private Console console;

    @BeforeEach
    public void setUp() {
        console = new Console();
        console.setModel("PlayStation5");
        console.setManufacturer("Sony");
        console.setMemoryAmount("825GB SSD");
        console.setProcessor("AMD");
        console.setPrice(BigDecimal.valueOf(49.99));
        console.setQuantity(100);

    }

    @Test
    public void shouldCreateNewConsole() throws Exception {
        when(consoleRepository.save(any(Console.class))).thenReturn(console);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/consoles")
                        .content(mapper.writeValueAsString(console))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.model").value("PlayStation5"))
                .andExpect(jsonPath("$.manufacturer").value("Sony"))
                .andExpect(jsonPath("$.memoryAmount").value("825GB SSD"))
                .andExpect(jsonPath("$.processor").value("AMD"))
                .andExpect(jsonPath("$.price").value(49.99))
                .andExpect(jsonPath("$.quantity").value(100));
    }

    @Test
    public void shouldGetAllConsoles() throws Exception {
        when(consoleRepository.findAll()).thenReturn(Arrays.asList(console));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/consoles"))
                .andExpect(jsonPath("$[0].model").value("PlayStation5"))
                .andExpect(jsonPath("$[0].manufacturer").value("Sony"))
                .andExpect(jsonPath("$[0].memoryAmount").value("825GB SSD"))
                .andExpect(jsonPath("$[0].processor").value("AMD"))
                .andExpect(jsonPath("$[0].price").value(49.99))
                .andExpect(jsonPath("$[0].quantity").value(100));
    }

    @Test
    public void shouldGetConsoleById() throws Exception {
        when(consoleRepository.findById(1)).thenReturn(Optional.of(console));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/consoles/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.model").value("PlayStation5"))
                .andExpect(jsonPath("$.manufacturer").value("Sony"))
                .andExpect(jsonPath("$.memoryAmount").value("825GB SSD"))
                .andExpect(jsonPath("$.processor").value("AMD"))
                .andExpect(jsonPath("$.price").value(49.99))
                .andExpect(jsonPath("$.quantity").value(100));
    }

    @Test
    public void shouldUpdateConsole() throws Exception {
        when(consoleRepository.save(any(Console.class))).thenReturn(console);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .put("/consoles/{id}", console.getConsoleId())
                        .content(mapper.writeValueAsString(console))
                        .contentType(MediaType.APPLICATION_JSON)

        ).andExpect(status().isNoContent());
    }

    @Test
    public void shouldDeleteConsoleById() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/consoles/{id}", 1)

        ).andExpect(status().isNoContent());
    }

    @Test
    public void shouldGetConsoleByManufacturer() throws Exception {
        when(consoleRepository.findByManufacturer("Sony")).thenReturn(Arrays.asList(console));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/consoles/manufacturers/{manufacturer}", "Sony"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].model").value("PlayStation5"))
                .andExpect(jsonPath("$[0].manufacturer").value("Sony"))
                .andExpect(jsonPath("$[0].memoryAmount").value("825GB SSD"))
                .andExpect(jsonPath("$[0].processor").value("AMD"))
                .andExpect(jsonPath("$[0].price").value(49.99))
                .andExpect(jsonPath("$[0].quantity").value(100));
    }


}