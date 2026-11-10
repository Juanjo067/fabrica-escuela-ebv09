package com.citas.app.CitasBack.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.citas.app.CitasBack.dto.RestablecerRequest;
import com.citas.app.CitasBack.model.TokenRecuperacion;
import com.citas.app.CitasBack.model.Usuario;
import com.citas.app.CitasBack.repository.TokenRecuperacionRepository;
import com.citas.app.CitasBack.repository.UsuarioRepository;
import com.citas.app.CitasBack.util.Reloj;

@Service
public class RecuperacionService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TokenRecuperacionRepository tokenRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${recuperacion.minutos-expiracion:30}")
    private long minutosExpiracion;

    @Value("${recuperacion.url-frontend:http://localhost:3000/restablecer}")
    private String urlFrontend;

    @Value("${recuperacion.remitente:citas@ejemplo.com}")
    private String remitente;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void solicitar(String correo) {
        usuarioRepository.findByCorreo(correo).ifPresent(usuario -> {
            TokenRecuperacion t = new TokenRecuperacion();
            t.setUsuario(usuario);
            t.setToken(UUID.randomUUID().toString());
            t.setExpiracion(Reloj.ahora().plusMinutes(minutosExpiracion));
            tokenRepository.save(t);

            try {
                SimpleMailMessage mail = new SimpleMailMessage();
                mail.setTo(usuario.getCorreo());
                mail.setFrom(remitente);
                mail.setSubject("Recuperar contraseña");
                mail.setText("Para restablecer tu contraseña entra a: " + urlFrontend + "?token=" + t.getToken()
                    + "\nEl enlace vence en " + minutosExpiracion + " minutos.");
                mailSender.send(mail);
            } catch (Exception e) {
                System.err.println("No se pudo enviar el correo de recuperación: " + e.getMessage());
            }
        });
    }

    @Transactional
    public void restablecer(RestablecerRequest request) {
        if (!request.getContrasena().equals(request.getConfirmarContrasena())) {
            throw new IllegalArgumentException("Las contraseñas no coinciden");
        }

        TokenRecuperacion t = tokenRepository.findByToken(request.getToken())
            .orElseThrow(() -> new IllegalArgumentException("El enlace no es válido o ya expiró"));

        if (t.isUsado() || t.getExpiracion().isBefore(LocalDateTime.now(Reloj.ZONA))) {
            throw new IllegalArgumentException("El enlace no es válido o ya expiró");
        }

        Usuario usuario = t.getUsuario();
        usuario.setContrasenaHash(passwordEncoder.encode(request.getContrasena()));
        usuario.setIntentosFallidos(0);     
        usuario.setBloqueadoHasta(null);
        usuarioRepository.save(usuario);

        t.setUsado(true);
        tokenRepository.save(t);
    }
}