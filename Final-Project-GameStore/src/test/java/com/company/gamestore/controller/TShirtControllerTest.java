package com.company.gamestore.controller;


import com.company.gamestore.model.TShirt;
import com.company.gamestore.repository.TShirtRepository;
import com.company.gamestore.service.ServiceLayer;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(TShirtController.class)
@AutoConfigureMockMvc(addFilters = false)
public class TShirtControllerTest {


    @Autowired
    private MockMvc mockMvc;
    private final ObjectMapper mapper = new ObjectMapper();
    @MockBean
    private TShirtRepository tShirtRepository;
    @MockBean
    private ServiceLayer serviceLayer;
    private TShirt tShirt;

    @BeforeEach
    public void setUp() {
        tShirt = new TShirt();
        tShirt.setTshirtId(1);
        tShirt.setSize("M");
        tShirt.setColor("Red");
        tShirt.setDescription("TShirt description");
        tShirt.setPrice(BigDecimal.valueOf(19.99));
        tShirt.setQuantity(20);
    }

    @Test
    public void shouldCreateNewTShirt() throws Exception {
        when(tShirtRepository.save(any(TShirt.class))).thenReturn(tShirt);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/tshirts")
                        .content(mapper.writeValueAsString(tShirt))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.size").value("M"))
                .andExpect(jsonPath("$.color").value("Red"))
                .andExpect(jsonPath("$.description").value("TShirt description"))
                .andExpect(jsonPath("$.price").value(19.99))
                .andExpect(jsonPath("$.quantity").value(20));
    }


    @Test
    public void shouldGetAllTShirts() throws Exception {
        when(tShirtRepository.findAll()).thenReturn(Arrays.asList(tShirt));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/tshirts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].size").value("M"))
                .andExpect(jsonPath("$[0].color").value("Red"))
                .andExpect(jsonPath("$[0].description").value("TShirt description"))
                .andExpect(jsonPath("$[0].price").value(19.99))
                .andExpect(jsonPath("$[0].quantity").value(20));
    }

    @Test
    public void shouldGetTShirtById() throws Exception {
        when(tShirtRepository.findById(1)).thenReturn(Optional.of(tShirt));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/tshirts/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size").value("M"))
                .andExpect(jsonPath("$.color").value("Red"))
                .andExpect(jsonPath("$.description").value("TShirt description"))
                .andExpect(jsonPath("$.price").value(19.99))
                .andExpect(jsonPath("$.quantity").value(20));
    }

    @Test
    public void shouldGetTShirtBySize() throws Exception {
        when(tShirtRepository.findBySize("M")).thenReturn(Arrays.asList(tShirt));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/tshirts/sizes/{size}", "M"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].size").value("M"))
                .andExpect(jsonPath("$[0].color").value("Red"))
                .andExpect(jsonPath("$[0].description").value("TShirt description"))
                .andExpect(jsonPath("$[0].price").value(19.99))
                .andExpect(jsonPath("$[0].quantity").value(20));
    }

    @Test
    public void shouldGetTShirtByColor() throws Exception {
        when(tShirtRepository.findByColor("Red")).thenReturn(Arrays.asList(tShirt));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/tshirts/colors/{color}", "Red"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].size").value("M"))
                .andExpect(jsonPath("$[0].color").value("Red"))
                .andExpect(jsonPath("$[0].description").value("TShirt description"))
                .andExpect(jsonPath("$[0].price").value(19.99))
                .andExpect(jsonPath("$[0].quantity").value(20));
    }


    @Test
    public void shouldUpdateTShirt() throws Exception {
        when(tShirtRepository.save(any(TShirt.class))).thenReturn(tShirt);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .put("/tshirts/{id}", tShirt.getTshirtId())
                        .content(mapper.writeValueAsString(tShirt))
                        .contentType(MediaType.APPLICATION_JSON)

        ).andExpect(status().isNoContent());
    }


    @Test
    public void shouldDeleteTShirtById() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/tshirts/{id}", 1)

        ).andExpect(status().isNoContent());
    }


}
