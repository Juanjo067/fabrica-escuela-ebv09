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

    // HU-13: consultar citas de un usuario (404 si el usuario no existe).
    public List<Cita> consultarCitasPorUsuario(Long idUsuario) {
        if (!usuarioRepository.existsById(idUsuario)) {
            throw new NoSuchElementException("No existe un usuario con id " + idUsuario);
        }
        return citaRepository.findByUsuarioIdUsuario(idUsuario);
    }
}