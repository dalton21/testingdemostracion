package com.example.repository;

import com.example.model.Nodo;
import com.example.repository.NodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class NodoRepositoryTests {

    @Autowired
    private NodoRepository nodoRepository;

    private Nodo nodo;

    @BeforeEach
    public void setup(){
        nodo = Nodo.builder()
                .titulo("Titulo1")
                .cuerpo("Cuerpo1")
                .build();
    }
    // JUnit test for save nodo operation
    //@DisplayName("JUnit test for save nodo operation")
    @Test
    public void givenNodoObject_whenSave_thenReturnSavedNodo(){

        //given - precondition or setup
        Nodo nodo = Nodo.builder()
                .titulo("Titulo2")
                .cuerpo("Cuerpo2")
                .build();
        // when - action or the behaviour that we are going test
        Nodo savedNodo = nodoRepository.save(nodo);

        // then - verify the output
        assertThat(savedNodo).isNotNull();
        assertThat(savedNodo.getId()).isGreaterThan(0);
    }


    // JUnit test for get all nodos operation
    @DisplayName("JUnit test for get all nodos operation")
    @Test
    public void givenNodosList_whenFindAll_thenNodosList(){
        // given - precondition or setup
//        Nodo nodo = Nodo.builder()
//                .firstName("Ramesh")
//                .lastName("Ramesh")
//                .email("ramesh@gmail,com")
//                .build();

        Nodo nodo1 = Nodo.builder()
                .titulo("Titulo1")
                .cuerpo("Cuerpo1")
                .build();

        nodoRepository.save(nodo);
        nodoRepository.save(nodo1);

        // when -  action or the behaviour that we are going test
        List<Nodo> nodoList = nodoRepository.findAll();

        // then - verify the output
        assertThat(nodoList).isNotNull();
        assertThat(nodoList.size()).isEqualTo(2);

    }

    // JUnit test for get nodo by id operation
    @DisplayName("JUnit test for get nodo by id operation")
    @Test
    public void givenNodoObject_whenFindById_thenReturnNodoObject(){
        // given - precondition or setup
//        Nodo nodo = Nodo.builder()
//                .firstName("Ramesh")
//                .lastName("Ramesh")
//                .email("ramesh@gmail,com")
//                .build();
        nodoRepository.save(nodo);

        // when -  action or the behaviour that we are going test
        Nodo nodoDB = nodoRepository.findById(nodo.getId()).get();

        // then - verify the output
        assertThat(nodoDB).isNotNull();
    }

    // JUnit test for get nodo by email operation
    @DisplayName("JUnit test for get nodo by email operation")
    @Test
    public void givenNodoEmail_whenFindByEmail_thenReturnNodoObject(){
        // given - precondition or setup
//        Nodo nodo = Nodo.builder()
//                .firstName("Ramesh")
//                .lastName("Fadatare")
//                .email("ramesh@gmail,com")
//                .build();
        nodoRepository.save(nodo);

        // when -  action or the behaviour that we are going test
        Nodo nodoDB = nodoRepository.findByTitulo(nodo.getTitulo()).get();

        // then - verify the output
        assertThat(nodoDB).isNotNull();
    }

    // JUnit test for update nodo operation
    @DisplayName("JUnit test for update nodo operation")
    @Test
    public void givenNodoObject_whenUpdateNodo_thenReturnUpdatedNodo(){
        // given - precondition or setup
        nodoRepository.save(nodo);

        // when -  action or the behaviour that we are going test
        Nodo savedNodo = nodoRepository.findById(nodo.getId()).get();
        savedNodo.setTitulo("Titulo");
        savedNodo.setCuerpo("Cuerpo");
        Nodo updatedNodo =  nodoRepository.save(savedNodo);

        // then - verify the output
        assertThat(updatedNodo.getTitulo()).isEqualTo("Titulo");
        assertThat(updatedNodo.getCuerpo()).isEqualTo("Cuerpo");
    }

    // JUnit test for delete nodo operation
    @DisplayName("JUnit test for delete nodo operation")
    @Test
    public void givenNodoObject_whenDelete_thenRemoveNodo(){
        // given - precondition or setup
        nodoRepository.save(nodo);

        // when -  action or the behaviour that we are going test
        nodoRepository.deleteById(nodo.getId());
        Optional<Nodo> nodoOptional = nodoRepository.findById(nodo.getId());

        // then - verify the output
        assertThat(nodoOptional).isEmpty();
    }


}
