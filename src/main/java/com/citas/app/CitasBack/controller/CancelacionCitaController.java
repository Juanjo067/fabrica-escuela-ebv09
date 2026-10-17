package com.citas.app.CitasBack.controller;

import com.citas.app.CitasBack.dto.CancelarCitaRequest;
import com.citas.app.CitasBack.dto.CancelarCitaResponse;
import com.citas.app.CitasBack.model.Cita;
import com.citas.app.CitasBack.service.CancelacionCitaService;
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
public class CancelacionCitaController {

    @Autowired
    private CancelacionCitaService cancelacionCitaService;

    @PutMapping("/{idCita}/cancelar")
    public ResponseEntity<?> cancelar(@PathVariable Long idCita,
                                      @Valid @RequestBody CancelarCitaRequest request) {
        try {
            Cita cita = cancelacionCitaService.cancelar(idCita, request.getIdUsuario(), request.getMotivo());
            return ResponseEntity.ok(new CancelarCitaResponse(cita));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            // Mismo patrón que en los demás controllers: regla de negocio inválida -> 400.
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
