package com.citas.app.CitasBack.controller;

import com.citas.app.CitasBack.dto.EspecialidadEdicionRequest; // Importación añadida
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
import com.citas.app.CitasBack.dto.EspecialidadResponse;
import com.citas.app.CitasBack.dto.EspecialidadDetalleResponse;

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

    // --- NUEVO ENDPOINT HU-07 (Task 100) ---
    @PutMapping("/{idEspecialidad}")
    public ResponseEntity<?> actualizar(@PathVariable Long idEspecialidad, 
                                        @Valid @RequestBody EspecialidadEdicionRequest request) {
        try {
            Especialidad actualizada = especialidadService.actualizarEspecialidad(idEspecialidad, request);
            return ResponseEntity.ok(actualizada);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            // Retorna 400 BAD_REQUEST si intenta cambiar la duración con citas pendientes
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // HU-08 (Task 156): Endpoint para listar especialidades activas
    @GetMapping("/activas")
    public ResponseEntity<?> listarActivas() {
        List<EspecialidadResponse> activas = especialidadService.listarEspecialidadesActivas();
        
        // Escenario 2: Informar que no hay especialidades disponibles
        if (activas.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No hay especialidades disponibles en este momento.");
        }
        
        // Escenario 1: Mostrar la lista de especialidades activas
        return ResponseEntity.ok(activas);
    }

    // HU-08 (Task 157): Endpoint para consultar detalle de especialidad
    @GetMapping("/{idEspecialidad}/detalle")
    public ResponseEntity<?> obtenerDetalle(@PathVariable Long idEspecialidad) {
        try {
            EspecialidadDetalleResponse detalle = especialidadService.obtenerDetalleEspecialidad(idEspecialidad);
            return ResponseEntity.ok(detalle);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
