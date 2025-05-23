package com.example.integration;

import com.example.TestingdemostracionApplication;
import com.example.model.Nodo;
import com.example.repository.NodoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(
        classes = TestingdemostracionApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
public class NodoControllerITests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private NodoRepository nodoRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        nodoRepository.deleteAll();
    }

    @Test
    public void givenNodoObject_whenCreateNodo_thenReturnSavedNodo() throws Exception {
        Nodo nodo = Nodo.builder()
                .titulo("Ramesh")
                .cuerpo("Fadatare")
                .build();

        mockMvc.perform(post("/nodos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nodo)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.titulo", is("Ramesh")));
    }

    @Test
    public void givenNodoId_whenGetNodoById_thenReturnNodoObject() throws Exception {
        Nodo saved = nodoRepository.save(Nodo.builder()
                .titulo("A")
                .cuerpo("B")
                .build());

        mockMvc.perform(get("/nodos/{id}", saved.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo", is("A")));
    }

    @Test
    public void givenInvalidNodoId_whenGetNodoById_thenReturnEmpty() throws Exception {
        mockMvc.perform(get("/nodos/{id}", 123L))
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenListOfNodos_whenGetAllNodos_thenReturnNodosList() throws Exception {
        nodoRepository.save(Nodo.builder().titulo("X").cuerpo("Y").build());
        nodoRepository.save(Nodo.builder().titulo("Z").cuerpo("W").build());

        mockMvc.perform(get("/nodos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }


}
