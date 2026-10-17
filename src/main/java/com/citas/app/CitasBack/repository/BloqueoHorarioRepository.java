package com.citas.app.CitasBack.repository;

import com.citas.app.CitasBack.model.BloqueoHorario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BloqueoHorarioRepository extends JpaRepository<BloqueoHorario, Long> {

    // "especialista" hereda su id de Usuario (idUsuario), por eso la propiedad
    // se llama Especialista_IdUsuario (igual que en DisponibilidadHorarioRepository).
    List<BloqueoHorario> findByEspecialista_IdUsuarioAndFechaAndEstado(
            Long idEspecialista, LocalDate fecha, String estado);

    List<BloqueoHorario> findByEspecialista_IdUsuarioAndEstadoOrderByFechaAscHoraInicioAsc(
            Long idEspecialista, String estado);
}
