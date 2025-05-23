package com.example.service;

import com.example.model.Nodo;

import java.util.List;
import java.util.Optional;

public interface NodoService {
    Nodo saveNodo(Nodo employee);
    List<Nodo> getAllNodos();
    Optional<Nodo> getNodoById(long id);
}
