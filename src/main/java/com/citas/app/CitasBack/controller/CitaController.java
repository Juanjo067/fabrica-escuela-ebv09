package com.citas.app.CitasBack.controller;

import com.citas.app.CitasBack.model.Cita;
import com.citas.app.CitasBack.service.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/citas")
public class CitaController {

    @Autowired
    private CitaService citaService;

    // HU-13: Consultar citas de un usuario
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<?> consultarCitas(@PathVariable Long idUsuario) {
        try {
            List<Cita> citas = citaService.consultarCitasPorUsuario(idUsuario);
            return ResponseEntity.ok(citas);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
