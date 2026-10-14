package com.citas.app.CitasBack.service;

import com.citas.app.CitasBack.model.Cita;
import com.citas.app.CitasBack.repository.CitaRepository;
import com.citas.app.CitasBack.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CitaService {

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // HU-13: Consultar citas
    public List<Cita> consultarCitasPorUsuario(Long idUsuario) {

        // Valida que el usuario exista
        if (!usuarioRepository.existsById(idUsuario)) {
            throw new NoSuchElementException("No existe un usuario con id " + idUsuario);
        }

        // Escenario 1: lista con especialidad, especialista, fecha, hora y estado
        // Escenario 2: si no tiene citas, devuelve lista vacía (el frontend muestra el mensaje)
        return citaRepository.findByUsuarioIdUsuario(idUsuario);
    }
}
