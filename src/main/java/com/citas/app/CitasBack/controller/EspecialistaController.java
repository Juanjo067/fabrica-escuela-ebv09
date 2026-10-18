package com.citas.app.CitasBack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.citas.app.CitasBack.dto.EspecialistaRequest;
import com.citas.app.CitasBack.dto.EspecialistaResponse;
import com.citas.app.CitasBack.model.Especialista;
import com.citas.app.CitasBack.service.EspecialistaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/especialistas")
public class EspecialistaController {

    @Autowired
    private EspecialistaService especialistaService;

    @PostMapping
    public ResponseEntity<?> registrar(@Valid @RequestBody EspecialistaRequest request) {
        try {
            // Llama a la lógica de negocio construida en el Task 96
            Especialista creado = especialistaService.registrarEspecialista(request);
            
            // Envuelve la entidad en el EspecialistaResponse (Task 95) para no exponer contraseñas
            return ResponseEntity.status(HttpStatus.CREATED).body(new EspecialistaResponse(creado));
            
        } catch (IllegalArgumentException e) {
            // Sigue el mismo patrón del equipo: regla de negocio inválida -> 400
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}