package com.citas.app.CitasBack.service;

import com.citas.app.CitasBack.dto.EspecialistaRequest;
import com.citas.app.CitasBack.model.Especialista;
import com.citas.app.CitasBack.repository.EspecialistaRepository;
import com.citas.app.CitasBack.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EspecialistaService {

    @Autowired
    private EspecialistaRepository especialistaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Transactional
    public Especialista registrarEspecialista(EspecialistaRequest request) {

        // 1. Validar que las contraseñas coincidan (Reutilizando la lógica de UsuarioService)
        if (!request.getContrasena().equals(request.getConfirmarContrasena())) {
            throw new IllegalArgumentException("Las contraseñas no coinciden");
        }

        // 2. Validar que el correo no exista en toda la plataforma (usuarios normales y especialistas)
        if (usuarioRepository.findByCorreo(request.getCorreo()).isPresent()) {
            throw new IllegalArgumentException("Ya existe una cuenta registrada con ese correo");
        }

        // 3. Crear y poblar la entidad Especialista
        Especialista especialista = new Especialista();
        
        // --- Campos heredados de Usuario ---
        especialista.setNombre(request.getNombre());
        especialista.setApellido(request.getApellido());
        especialista.setCorreo(request.getCorreo());
        especialista.setTelefono(request.getTelefono());
        // Encriptar la contraseña usando el mismo estándar del equipo
        especialista.setContrasenaHash(passwordEncoder.encode(request.getContrasena()));
        
        // --- Campos propios de Especialista ---
        especialista.setRegistroMedico(request.getRegistroMedico());

        // 4. Guardar en base de datos.
        return especialistaRepository.save(especialista);
    }
}