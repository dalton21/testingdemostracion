package com.example.service.impl;

import com.example.exception.ResourceNotFoundException;
import com.example.model.Nodo;
import com.example.repository.NodoRepository;
import com.example.service.NodoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NodoServiceImpl implements NodoService {

    private NodoRepository nodoRepository;

    public NodoServiceImpl(NodoRepository nodoRepository) {
        this.nodoRepository = nodoRepository;
    }

    @Override
    public Nodo saveNodo(Nodo nodo) {

        Optional<Nodo> savedNodo = nodoRepository.findByTitulo(nodo.getTitulo());
        if(savedNodo.isPresent()){
            throw new ResourceNotFoundException("Nodo already exist with given title:" + nodo.getTitulo());
        }
        return nodoRepository.save(nodo);
    }

    @Override
    public List<Nodo> getAllNodos() {
        return nodoRepository.findAll();
    }

    @Override
    public Optional<Nodo> getNodoById(long id) {
        return nodoRepository.findById(id);
    }

}
