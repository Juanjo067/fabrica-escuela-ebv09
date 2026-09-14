package com.citas.app.CitasBack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.citas.app.CitasBack.dto.UsuarioRequest;
import com.citas.app.CitasBack.dto.UsuarioResponse;
import com.citas.app.CitasBack.model.Usuario;
import com.citas.app.CitasBack.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<?> registrar(@Valid @RequestBody UsuarioRequest request) {
        try {
            Usuario creado = usuarioService.registrarUsuario(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(new UsuarioResponse(creado));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}