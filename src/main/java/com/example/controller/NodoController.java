package com.example.controller;

import com.example.model.Nodo;
import com.example.service.NodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/nodos")
public class NodoController {

    private NodoService nodoService;

    public NodoController(NodoService nodoService) {
        this.nodoService = nodoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Nodo createNodo(@RequestBody Nodo nodo){
        return nodoService.saveNodo(nodo);
    }

    @GetMapping
    public List<Nodo> getAllNodos(){
        return nodoService.getAllNodos();
    }

    @GetMapping("{id}")
    public ResponseEntity<Nodo> getNodoById(@PathVariable("id") long nodoId){
        return nodoService.getNodoById(nodoId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


}
