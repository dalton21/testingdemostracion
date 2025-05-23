package com.example.controller;

import com.example.model.Nodo;
import com.example.service.NodoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(NodoController.class)
class NodoControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private NodoService nodoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void givenNodoObject_whenCreateNodo_thenReturnSavedNodo() throws Exception {
        Nodo nodo = Nodo.builder()
                .titulo("TituloSave")
                .cuerpo("CuerpoSave")
                .build();

        given(nodoService.saveNodo(any(Nodo.class)))
                .willAnswer(inv -> inv.getArgument(0));

        mockMvc.perform(post("/nodos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nodo)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.titulo").value("TituloSave"))
                .andDo(print());
    }

    @Test
    void givenInvalidNodoId_whenGetNodoById_thenReturnEmpty() throws Exception {
        long nodoId = 42L;
        given(nodoService.getNodoById(nodoId)).willReturn(Optional.empty());

        mockMvc.perform(get("/nodos/{id}", nodoId))
                .andExpect(status().isNotFound())
                .andDo(print());
    }


}
