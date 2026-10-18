package com.citas.app.CitasBack.repository;

import com.citas.app.CitasBack.model.Cita;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface CitaRepository extends JpaRepository<Cita, Long> {

    // HU-13
    List<Cita> findByUsuarioIdUsuario(Long idUsuario);

    // HU-11: citas activas de un especialista en una fecha.
    List<Cita> findByEspecialista_IdUsuarioAndFechaCitaAndEstadoCitaNot(
            Long idEspecialista, LocalDate fechaCita, String estadoExcluido);

    // HU-15: ¿el especialista ya tiene una cita activa en esa fecha y hora?
    Optional<Cita> findByEspecialista_IdUsuarioAndFechaCitaAndHoraCitaAndEstadoCitaNot(
            Long idEspecialista, LocalDate fechaCita, LocalTime horaCita, String estadoExcluido);

        // HU-07 (Task 102): validar si la especialidad tiene citas en determinados estados (ej. SOLICITADA o CONFIRMADA)
    boolean existsByEspecialidad_IdEspecialidadAndEstadoCitaIn(
            Long idEspecialidad, List<String> estados);
}