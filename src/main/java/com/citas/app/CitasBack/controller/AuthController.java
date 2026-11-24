package com.citas.app.CitasBack.controller;

import com.citas.app.CitasBack.dto.LoginRequest;
import com.citas.app.CitasBack.dto.LoginResponse;
import com.citas.app.CitasBack.dto.UsuarioResponse;
import com.citas.app.CitasBack.model.Sesion;
import com.citas.app.CitasBack.model.Usuario;
import com.citas.app.CitasBack.service.AuthService;
import com.citas.app.CitasBack.service.SesionService;
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

    @Autowired
    private SesionService sesionService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        try {
            Usuario usuario = authService.login(request.getCorreo(), request.getContrasena());
            // HU-26: cada inicio de sesión genera un identificador nuevo.
            Sesion sesion = sesionService.crearSesion(usuario);
            return ResponseEntity.ok(new LoginResponse(new UsuarioResponse(usuario), sesion.getToken()));
        } catch (IllegalArgumentException e) {
            // Mismo patron que en los demas controllers: regla de negocio
            // invalida -> 400 con el mensaje en texto plano.
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // HU-26: cerrar sesión. El interceptor ya verificó que la sesión está activa.
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestAttribute("sesion") Sesion sesion) {
        sesionService.cerrarSesion(sesion.getToken());
        return ResponseEntity.ok("Sesión cerrada exitosamente");
    }

    // HU-26: ruta protegida de ejemplo. Sirve también para que el frontend
    // compruebe si la sesión sigue activa.
    @GetMapping("/sesion")
    public ResponseEntity<?> sesionActual(@RequestAttribute("sesion") Sesion sesion) {
        return ResponseEntity.ok(new UsuarioResponse(sesion.getUsuario()));
    }
}
