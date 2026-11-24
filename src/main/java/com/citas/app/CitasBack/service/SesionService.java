package com.citas.app.CitasBack.service;

import com.citas.app.CitasBack.model.Sesion;
import com.citas.app.CitasBack.model.Usuario;
import com.citas.app.CitasBack.repository.SesionRepository;
import com.citas.app.CitasBack.util.Reloj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class SesionService {

    @Autowired
    private SesionRepository sesionRepository;

    private final SecureRandom random = new SecureRandom();

    // HU-26 (tarea 206): cada inicio de sesión genera un identificador nuevo.
    @Transactional
    public Sesion crearSesion(Usuario usuario) {
        byte[] bytes = new byte[32];
        random.nextBytes(bytes);

        Sesion sesion = new Sesion();
        sesion.setToken(Base64.getUrlEncoder().withoutPadding().encodeToString(bytes));
        sesion.setUsuario(usuario);
        sesion.setFechaCreacion(Reloj.ahora());
        sesion.setActiva(true);
        return sesionRepository.save(sesion);
    }

    public Optional<Sesion> buscarActiva(String token) {
        return sesionRepository.findByTokenAndActivaTrue(token);
    }

    // HU-26 (tarea 204): invalida el identificador de sesión.
    @Transactional
    public void cerrarSesion(String token) {
        Sesion sesion = sesionRepository.findByTokenAndActivaTrue(token)
                .orElseThrow(() -> new NoSuchElementException("La sesión no existe o ya fue cerrada"));
        sesion.setActiva(false);
        sesion.setFechaCierre(Reloj.ahora());
        sesionRepository.save(sesion);
    }

    // El token llega en el encabezado: Authorization: Bearer <token>
    public static String extraerToken(String encabezado) {
        if (encabezado == null || !encabezado.startsWith("Bearer ")) {
            return null;
        }
        String token = encabezado.substring(7).trim();
        return token.isEmpty() ? null : token;
    }
}
