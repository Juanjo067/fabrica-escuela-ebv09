package com.citas.app.CitasBack.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.citas.app.CitasBack.model.EventoSeguridad;
import com.citas.app.CitasBack.model.Usuario;
import com.citas.app.CitasBack.repository.EventoSeguridadRepository;
import com.citas.app.CitasBack.repository.UsuarioRepository;
import com.citas.app.CitasBack.util.Reloj;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EventoSeguridadRepository eventoSeguridadRepository;

    @Value("${seguridad.login.max-intentos:5}")
    private int maxIntentos;

    @Value("${seguridad.login.minutos-bloqueo:15}")
    private long minutosBloqueo;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Transactional(noRollbackFor = IllegalArgumentException.class)
    public Usuario login(String correo, String contrasena) {
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(AuthService::credencialesInvalidas);

        LocalDateTime ahora = Reloj.ahora();

        if (usuario.getBloqueadoHasta() != null) {
            if (usuario.getBloqueadoHasta().isAfter(ahora)) {
                long minutos = java.time.Duration.between(ahora, usuario.getBloqueadoHasta()).toMinutes() + 1;
                throw new IllegalArgumentException(
                        "Cuenta bloqueada temporalmente. Intenta de nuevo en " + minutos + " minutos");
            }
            usuario.setBloqueadoHasta(null);
            usuario.setIntentosFallidos(0);
        }

        if (!passwordEncoder.matches(contrasena, usuario.getContrasenaHash())) {
            int intentos = (usuario.getIntentosFallidos() == null ? 0 : usuario.getIntentosFallidos()) + 1;
            usuario.setIntentosFallidos(intentos);

            if (intentos >= maxIntentos) {
                usuario.setBloqueadoHasta(ahora.plusMinutes(minutosBloqueo));
                usuario.setIntentosFallidos(0);
                registrarEvento(usuario, "CUENTA_BLOQUEADA",
                        maxIntentos + " intentos fallidos de inicio de sesión", ahora);
                usuarioRepository.save(usuario);
                throw new IllegalArgumentException(
                        "Cuenta bloqueada por " + minutosBloqueo + " minutos por demasiados intentos fallidos");
            }

            usuarioRepository.save(usuario);
            throw credencialesInvalidas();
        }

        usuario.setIntentosFallidos(0);
        return usuarioRepository.save(usuario);
    }

    private void registrarEvento(Usuario usuario, String tipo, String detalle, LocalDateTime fecha) {
        EventoSeguridad evento = new EventoSeguridad();
        evento.setUsuario(usuario);
        evento.setTipo(tipo);
        evento.setDetalle(detalle);
        evento.setFecha(fecha);
        eventoSeguridadRepository.save(evento);
    }

    private static IllegalArgumentException credencialesInvalidas() {
        return new IllegalArgumentException("El correo o la contraseña no son validos");
    }
}