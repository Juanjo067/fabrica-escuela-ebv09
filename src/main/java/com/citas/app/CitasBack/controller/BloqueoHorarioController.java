package com.citas.app.CitasBack.controller;

import com.citas.app.CitasBack.dto.BloqueoMensajeResponse;
import com.citas.app.CitasBack.dto.BloqueoRequest;
import com.citas.app.CitasBack.dto.BloqueoResponse;
import com.citas.app.CitasBack.model.BloqueoHorario;
import com.citas.app.CitasBack.service.BloqueoHorarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api")
public class BloqueoHorarioController {

    @Autowired
    private BloqueoHorarioService bloqueoHorarioService;

    @PostMapping("/especialistas/{idEspecialista}/bloqueos")
    public ResponseEntity<?> bloquear(@PathVariable Long idEspecialista,
                                      @Valid @RequestBody BloqueoRequest request) {
        try {
            BloqueoHorario bloqueo = bloqueoHorarioService.bloquear(idEspecialista, request);
            return ResponseEntity.status(HttpStatus.CREATED).body(
                new BloqueoMensajeResponse("Horario bloqueado exitosamente", new BloqueoResponse(bloqueo)));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            // Mismo patrón que en los demás controllers: regla de negocio inválida -> 400.
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/especialistas/{idEspecialista}/bloqueos")
    public ResponseEntity<?> listar(@PathVariable Long idEspecialista,
                                    @RequestParam(required = false) String fecha) {
        try {
            List<BloqueoResponse> bloqueos = bloqueoHorarioService.listarActivos(idEspecialista, fecha)
                .stream().map(BloqueoResponse::new).toList();
            return ResponseEntity.ok(bloqueos);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/bloqueos/{idBloqueo}")
    public ResponseEntity<?> desbloquear(@PathVariable Long idBloqueo) {
        try {
            BloqueoHorario bloqueo = bloqueoHorarioService.desbloquear(idBloqueo);
            return ResponseEntity.ok(
                new BloqueoMensajeResponse("Horario desbloqueado exitosamente", new BloqueoResponse(bloqueo)));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
