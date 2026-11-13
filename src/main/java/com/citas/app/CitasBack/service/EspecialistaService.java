package com.citas.app.CitasBack.service;

import com.citas.app.CitasBack.dto.EspecialistaRequest;
import com.citas.app.CitasBack.model.Especialista;
import com.citas.app.CitasBack.repository.EspecialistaRepository;
import com.citas.app.CitasBack.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.citas.app.CitasBack.dto.EspecialistaResponse;
import com.citas.app.CitasBack.dto.EspecialistaDetalleResponse;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

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

    // HU-20 (Task 186): Listar especialistas activos y mapearlos al DTO
    public List<EspecialistaResponse> listarEspecialistasActivos() {
        return especialistaRepository.findByActivoTrue()
                .stream()
                .map(EspecialistaResponse::new) // Mapea de Entidad a DTO seguro
                .collect(Collectors.toList());
    }

    // HU-20 (Task 187): Consultar detalle de un especialista activo
    public EspecialistaDetalleResponse obtenerDetalleEspecialista(Long idUsuario) {
        Especialista especialista = especialistaRepository.findByIdUsuarioAndActivoTrue(idUsuario)
                .orElseThrow(() -> new NoSuchElementException("No se encontró el especialista o no está activo"));
        
        return new EspecialistaDetalleResponse(especialista);
    }
}