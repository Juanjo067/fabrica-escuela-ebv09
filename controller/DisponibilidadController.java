package com.citas.app.CitasBack.controller;

import com.citas.app.CitasBack.dto.DisponibilidadRequest;
import com.citas.app.CitasBack.dto.DisponibilidadResponse;
import com.citas.app.CitasBack.model.DisponibilidadHorario;
import com.citas.app.CitasBack.service.DisponibilidadService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/disponibilidad")
public class DisponibilidadController {

    @Autowired
    private DisponibilidadService disponibilidadService;

    @PostMapping
    public ResponseEntity<?> definirHorario(@Valid @RequestBody DisponibilidadRequest request) {
        try {
            DisponibilidadHorario creado = disponibilidadService.definirHorario(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(new DisponibilidadResponse(creado));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            // Mismo patron que UsuarioController con "las contrasenas no coinciden":
            // regla de negocio invalida -> 400 con el mensaje en texto plano.
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/especialista/{idEspecialista}")
    public ResponseEntity<List<DisponibilidadResponse>> consultar(@PathVariable Long idEspecialista) {
        List<DisponibilidadResponse> horarios = disponibilidadService.consultarPorEspecialista(idEspecialista)
            .stream()
            .map(DisponibilidadResponse::new)
            .collect(Collectors.toList());
        return ResponseEntity.ok(horarios);
    }
}
