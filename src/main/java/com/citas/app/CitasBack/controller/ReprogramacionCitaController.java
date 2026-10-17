package com.citas.app.CitasBack.controller;

import com.citas.app.CitasBack.dto.ReprogramarCitaRequest;
import com.citas.app.CitasBack.dto.ReprogramarCitaResponse;
import com.citas.app.CitasBack.model.ReprogramacionCita;
import com.citas.app.CitasBack.service.ReprogramacionCitaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/citas")
public class ReprogramacionCitaController {

    @Autowired
    private ReprogramacionCitaService reprogramacionCitaService;

    @PutMapping("/{idCita}/reprogramar")
    public ResponseEntity<?> reprogramar(@PathVariable Long idCita,
                                         @Valid @RequestBody ReprogramarCitaRequest request) {
        try {
            ReprogramacionCita reprogramacion = reprogramacionCitaService.reprogramar(idCita, request);
            return ResponseEntity.ok(new ReprogramarCitaResponse(reprogramacion.getCita(), reprogramacion));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            // Mismo patrón que en los demás controllers: regla de negocio inválida -> 400.
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
