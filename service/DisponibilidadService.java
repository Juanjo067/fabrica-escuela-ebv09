package com.citas.app.CitasBack.service;

import com.citas.app.CitasBack.dto.DisponibilidadRequest;
import com.citas.app.CitasBack.model.DisponibilidadHorario;
import com.citas.app.CitasBack.model.Especialista;
import com.citas.app.CitasBack.repository.DisponibilidadHorarioRepository;
import com.citas.app.CitasBack.repository.EspecialistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class DisponibilidadService {

    @Autowired
    private DisponibilidadHorarioRepository disponibilidadRepository;

    @Autowired
    private EspecialistaRepository especialistaRepository;

    public DisponibilidadHorario definirHorario(DisponibilidadRequest request) {

        Especialista especialista = especialistaRepository.findById(request.getIdEspecialista())
            .orElseThrow(() -> new NoSuchElementException(
                "No existe un especialista con id " + request.getIdEspecialista()
            ));

        // Regla de negocio: la hora de inicio debe ser anterior a la de fin.
        if (!request.getHoraInicio().isBefore(request.getHoraFin())) {
            throw new IllegalArgumentException("La hora de inicio debe ser anterior a la hora de fin");
        }

        // Escenario 2 del criterio de aceptacion: conflicto de horario.
        // No se puede superponer con un horario ya existente del mismo
        // especialista, en el mismo dia de la semana.
        List<DisponibilidadHorario> existentes = disponibilidadRepository
            .findByEspecialista_IdUsuarioAndDiaSemanaAndActivoTrue(request.getIdEspecialista(), request.getDiaSemana());

        boolean seSuperpone = existentes.stream().anyMatch(h ->
            request.getHoraInicio().isBefore(h.getHoraFin()) && h.getHoraInicio().isBefore(request.getHoraFin())
        );

        if (seSuperpone) {
            throw new IllegalArgumentException(
                "Ya existe un horario registrado que se superpone con el rango indicado para ese dia"
            );
        }

        DisponibilidadHorario horario = new DisponibilidadHorario();
        horario.setEspecialista(especialista);
        horario.setDiaSemana(request.getDiaSemana());
        horario.setHoraInicio(request.getHoraInicio());
        horario.setHoraFin(request.getHoraFin());
        horario.setActivo(true);

        return disponibilidadRepository.save(horario);
    }

    public List<DisponibilidadHorario> consultarPorEspecialista(Long idEspecialista) {
        return disponibilidadRepository.findByEspecialista_IdUsuarioAndActivoTrue(idEspecialista);
    }
}
