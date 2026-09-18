package com.citas.app.CitasBack.dto;

import com.citas.app.CitasBack.model.DisponibilidadHorario;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class DisponibilidadResponse {

    private final Long idDisponibilidad;
    private final DayOfWeek diaSemana;
    private final LocalTime horaInicio;
    private final LocalTime horaFin;
    private final Boolean activo;
    private final Long idEspecialista;

    public DisponibilidadResponse(DisponibilidadHorario horario) {
        this.idDisponibilidad = horario.getIdDisponibilidad();
        this.diaSemana = horario.getDiaSemana();
        this.horaInicio = horario.getHoraInicio();
        this.horaFin = horario.getHoraFin();
        this.activo = horario.getActivo();
        this.idEspecialista = horario.getEspecialista().getIdUsuario();
    }

    public Long getIdDisponibilidad() { return idDisponibilidad; }
    public DayOfWeek getDiaSemana() { return diaSemana; }
    public LocalTime getHoraInicio() { return horaInicio; }
    public LocalTime getHoraFin() { return horaFin; }
    public Boolean getActivo() { return activo; }
    public Long getIdEspecialista() { return idEspecialista; }
}
