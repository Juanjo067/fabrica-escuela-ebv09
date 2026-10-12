package com.citas.app.CitasBack.service;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.citas.app.CitasBack.dto.PerfilRequest;
import com.citas.app.CitasBack.dto.UsuarioRequest;
import com.citas.app.CitasBack.model.Usuario;
import com.citas.app.CitasBack.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Usuario registrarUsuario(UsuarioRequest request) {

        if (!request.getContrasena().equals(request.getConfirmarContrasena())) {
            throw new IllegalArgumentException("Las contraseñas no coinciden");
        }

        if (usuarioRepository.findByCorreo(request.getCorreo()).isPresent()) {
            throw new IllegalArgumentException("Ya existe una cuenta registrada con ese correo");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setApellido(request.getApellido());
        usuario.setCorreo(request.getCorreo());
        usuario.setTelefono(request.getTelefono());
        usuario.setContrasenaHash(passwordEncoder.encode(request.getContrasena()));

        return usuarioRepository.save(usuario);
    }

    public Usuario consultarPerfil(Long id) {
        return usuarioRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException(
                "No existe un usuario con id " + id
            ));
    }

    public Usuario actualizarPerfil(Long id, PerfilRequest request) {

        Usuario usuario = consultarPerfil(id);

        usuarioRepository.findByCorreo(request.getCorreo())
            .filter(otro -> !otro.getIdUsuario().equals(id))
            .ifPresent(otro -> {
                throw new IllegalArgumentException("Ya existe una cuenta registrada con ese correo");
            });

        usuario.setNombre(request.getNombre());
        usuario.setApellido(request.getApellido());
        usuario.setCorreo(request.getCorreo());
        usuario.setTelefono(request.getTelefono());

        return usuarioRepository.save(usuario);
    }
}