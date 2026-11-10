package com.citas.app.CitasBack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.citas.app.CitasBack.dto.RecuperarRequest;
import com.citas.app.CitasBack.dto.RestablecerRequest;
import com.citas.app.CitasBack.service.RecuperacionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class RecuperacionController {

    @Autowired
    private RecuperacionService recuperacionService;

    @PostMapping("/recuperar")
    public ResponseEntity<?> recuperar(@Valid @RequestBody RecuperarRequest request) {
        recuperacionService.solicitar(request.getCorreo());
        return ResponseEntity.ok("Si el correo está registrado, te enviamos un enlace para recuperar tu contraseña");
    }

    @PostMapping("/restablecer")
    public ResponseEntity<?> restablecer(@Valid @RequestBody RestablecerRequest request) {
        try {
            recuperacionService.restablecer(request);
            return ResponseEntity.ok("Contraseña actualizada exitosamente");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}