package com.citas.app.CitasBack.service;

import com.citas.app.CitasBack.model.Usuario;
import com.citas.app.CitasBack.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario login(String correo, String contrasena) {
        Usuario usuario = usuarioRepository.findByCorreo(correo)
            .orElseThrow(AuthService::credencialesInvalidas);

        if (!contrasena.equals(usuario.getContrasenaHash())) {
            throw credencialesInvalidas();
        }

        return usuario;
    }

    private static IllegalArgumentException credencialesInvalidas() {
        return new IllegalArgumentException("El correo o la contrasena no son validos");
    }
}
