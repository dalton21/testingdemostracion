package com.example.repository;

import com.example.model.Nodo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NodoRepository extends JpaRepository<Nodo, Long> {

    Optional<Nodo> findByTitulo(String titulo);

}
