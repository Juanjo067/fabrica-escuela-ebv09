package com.citas.app.CitasBack.repository;

import com.citas.app.CitasBack.model.DisponibilidadHorario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;
import java.util.List;

public interface DisponibilidadHorarioRepository extends JpaRepository<DisponibilidadHorario, Long> {

    // "especialista" hereda su id de Usuario (idUsuario), por eso la propiedad
    // se llama Especialista_IdUsuario y no Especialista_IdEspecialista.
    List<DisponibilidadHorario> findByEspecialista_IdUsuarioAndDiaSemanaAndActivoTrue(
            Long idEspecialista, DayOfWeek diaSemana);

    List<DisponibilidadHorario> findByEspecialista_IdUsuarioAndActivoTrue(Long idEspecialista);
}
