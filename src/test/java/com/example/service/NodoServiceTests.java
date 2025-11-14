package com.example.service;

import com.example.exception.ResourceNotFoundException;
import com.example.model.Nodo;
import com.example.repository.NodoRepository;
import com.example.service.impl.NodoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class NodoServiceTests {

    @Mock
    private NodoRepository nodoRepository;

    @InjectMocks
    private NodoServiceImpl nodoService;

    private Nodo nodo;

    @BeforeEach
    public void setup(){
        nodo = Nodo.builder()
                .id(1L)
                .titulo("titulo")
                .cuerpo("cuerpo")
                .build();
    }

    // JUnit test for saveNodo method
    @DisplayName("JUnit test for saveNodo method")
    @Test
    public void givenNodoObject_whenSaveNodo_thenReturnNodoObject(){
        // given - precondition or setup
        given(nodoRepository.findByTitulo(nodo.getTitulo()))
                .willReturn(Optional.empty());

        given(nodoRepository.save(nodo)).willReturn(nodo);

        System.out.println(nodoRepository);
        System.out.println(nodoService);

        // when -  action or the behaviour that we are going test
        Nodo savedNodo = nodoService.saveNodo(nodo);

        System.out.println(savedNodo);
        // then - verify the output
        assertThat(savedNodo).isNotNull();
    }

    // JUnit test for saveNodo method
    @DisplayName("JUnit test for saveNodo method which throws exception")
    @Test
    public void givenExistingEmail_whenSaveNodo_thenThrowsException(){
        // given - precondition or setup
        given(nodoRepository.findByTitulo(nodo.getTitulo()))
                .willReturn(Optional.of(nodo));

        System.out.println(nodoRepository);
        System.out.println(nodoService);

        // when -  action or the behaviour that we are going test
        org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            nodoService.saveNodo(nodo);
        });

        // then
        verify(nodoRepository, never()).save(any(Nodo.class));
    }

    // JUnit test for getAllNodos method
    @DisplayName("JUnit test for getAllNodos method")
    @Test
    public void givenNodosList_whenGetAllNodos_thenReturnNodosList(){
        // given - precondition or setup

        Nodo nodo1 = Nodo.builder()
                .id(2L)
                .titulo("titulo2")
                .cuerpo("cuerpo2")
                .build();

        given(nodoRepository.findAll()).willReturn(List.of(nodo,nodo1));

        // when -  action or the behaviour that we are going test
        List<Nodo> nodoList = nodoService.getAllNodos();

        // then - verify the output
        assertThat(nodoList).isNotNull();
        assertThat(nodoList.size()).isEqualTo(2);
    }

    // JUnit test for getAllNodos method
    @DisplayName("JUnit test for getAllNodos method (negative scenario)")
    @Test
    public void givenEmptyNodosList_whenGetAllNodos_thenReturnEmptyNodosList(){
        // given - precondition or setup

        Nodo nodo1 = Nodo.builder()
                .id(2L)
                .titulo("titulo2")
                .cuerpo("cuerpo2")
                .build();

        given(nodoRepository.findAll()).willReturn(Collections.emptyList());

        // when -  action or the behaviour that we are going test
        List<Nodo> nodoList = nodoService.getAllNodos();

        // then - verify the output
        assertThat(nodoList).isEmpty();
        assertThat(nodoList.size()).isEqualTo(0);
    }

    // JUnit test for getNodoById method
    @DisplayName("JUnit test for getNodoById method")
    @Test
    public void givenNodoId_whenGetNodoById_thenReturnNodoObject(){
        // given
        given(nodoRepository.findById(1L)).willReturn(Optional.of(nodo));

        // when
        Nodo savedNodo = nodoService.getNodoById(nodo.getId()).get();

        // then
        assertThat(savedNodo).isNotNull();

    }


}
