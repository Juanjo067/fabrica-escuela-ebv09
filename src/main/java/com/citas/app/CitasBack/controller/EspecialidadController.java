package com.citas.app.CitasBack.controller;

import com.citas.app.CitasBack.dto.EspecialidadRequest;
import com.citas.app.CitasBack.model.Especialidad;
import com.citas.app.CitasBack.service.EspecialidadService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/especialidades")
public class EspecialidadController {

    @Autowired
    private EspecialidadService especialidadService;

    @PostMapping
    public ResponseEntity<?> registrar(@Valid @RequestBody EspecialidadRequest request) {
        try {
            Especialidad creada = especialidadService.registrarEspecialidad(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(creada);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Especialidad>> listar() {
        return ResponseEntity.ok(especialidadService.listarEspecialidades());
    }
}
