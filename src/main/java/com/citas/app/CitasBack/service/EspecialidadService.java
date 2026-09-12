package com.citas.app.CitasBack.service;

import com.citas.app.CitasBack.dto.EspecialidadRequest;
import com.citas.app.CitasBack.model.Especialidad;
import com.citas.app.CitasBack.model.Especialista;
import com.citas.app.CitasBack.repository.EspecialidadRepository;
import com.citas.app.CitasBack.repository.EspecialistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class EspecialidadService {

    @Autowired
    private EspecialidadRepository especialidadRepository;

    @Autowired
    private EspecialistaRepository especialistaRepository;

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
}
