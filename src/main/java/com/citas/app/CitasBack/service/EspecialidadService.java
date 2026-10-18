package com.citas.app.CitasBack.service;

import com.citas.app.CitasBack.dto.EspecialidadEdicionRequest;
import com.citas.app.CitasBack.dto.EspecialidadRequest;
import com.citas.app.CitasBack.model.Especialidad;
import com.citas.app.CitasBack.model.Especialista;
import com.citas.app.CitasBack.model.EstadoCita;
import com.citas.app.CitasBack.repository.CitaRepository;
import com.citas.app.CitasBack.repository.EspecialidadRepository;
import com.citas.app.CitasBack.repository.EspecialistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class EspecialidadService {

    @Autowired
    private EspecialidadRepository especialidadRepository;

    @Autowired
    private EspecialistaRepository especialistaRepository;

    // Repositorio añadido para la Task 102
    @Autowired
    private CitaRepository citaRepository;

    public Especialidad registrarEspecialidad(EspecialidadRequest request) {

        Especialista especialista = especialistaRepository.findById(request.getIdEspecialista())
            .orElseThrow(() -> new NoSuchElementException(
                "No existe un especialista con id " + request.getIdEspecialista()
            ));

        Especialidad especialidad = new Especialidad();
        especialidad.setNombreEspecialidad(request.getNombreEspecialidad());
        especialidad.setDescripcion(request.getDescripcion());
        especialidad.setDuracionMinutos(request.getDuracionMinutos());
        especialidad.setEspecialista(especialista);

        return especialidadRepository.save(especialidad);
    }

    public List<Especialidad> listarEspecialidades() {
        return especialidadRepository.findAll();
    }

    // Método añadido para la Task 102 que tu controlador está intentando llamar
    @Transactional
    public Especialidad actualizarEspecialidad(Long idEspecialidad, EspecialidadEdicionRequest request) {
        
        Especialidad especialidad = especialidadRepository.findById(idEspecialidad)
            .orElseThrow(() -> new NoSuchElementException(
                "No existe una especialidad con id " + idEspecialidad
            ));

        if (!especialidad.getDuracionMinutos().equals(request.getDuracionMinutos())) {
            
            List<String> estadosActivos = Arrays.asList(EstadoCita.SOLICITADA, EstadoCita.CONFIRMADA);
            boolean tieneCitasPendientes = citaRepository
                .existsByEspecialidad_IdEspecialidadAndEstadoCitaIn(idEspecialidad, estadosActivos);
            
            if (tieneCitasPendientes) {
                throw new IllegalArgumentException(
                    "No se puede modificar la duración porque la especialidad tiene citas pendientes. " +
                    "Cancele o reprograme las citas antes de cambiar la duración."
                );
            }
        }

        especialidad.setNombreEspecialidad(request.getNombreEspecialidad());
        especialidad.setDescripcion(request.getDescripcion());
        especialidad.setDuracionMinutos(request.getDuracionMinutos());

        return especialidadRepository.save(especialidad);
    }
}
