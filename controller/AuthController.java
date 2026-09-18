package com.citas.app.CitasBack.controller;

import com.citas.app.CitasBack.dto.LoginRequest;
import com.citas.app.CitasBack.dto.LoginResponse;
import com.citas.app.CitasBack.dto.UsuarioResponse;
import com.citas.app.CitasBack.model.Usuario;
import com.citas.app.CitasBack.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        try {
            Usuario usuario = authService.login(request.getCorreo(), request.getContrasena());
            return ResponseEntity.ok(new LoginResponse(new UsuarioResponse(usuario)));
        } catch (IllegalArgumentException e) {
            // Mismo patron que en los demas controllers: regla de negocio
            // invalida -> 400 con el mensaje en texto plano.
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
