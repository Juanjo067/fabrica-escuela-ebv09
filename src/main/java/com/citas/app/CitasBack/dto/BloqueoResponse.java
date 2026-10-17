package com.citas.app.CitasBack.dto;

import com.citas.app.CitasBack.model.BloqueoHorario;

import java.time.LocalDate;
import java.time.LocalTime;

public class BloqueoResponse {

    private final Long idBloqueo;
    private final Long idEspecialista;
    private final LocalDate fecha;
    private final LocalTime horaInicio;
    private final LocalTime horaFin;
    private final String motivo;
    private final String estado;

    public BloqueoResponse(BloqueoHorario bloqueo) {
        this.idBloqueo = bloqueo.getIdBloqueo();
        this.idEspecialista = bloqueo.getEspecialista().getIdUsuario();
        this.fecha = bloqueo.getFecha();
        this.horaInicio = bloqueo.getHoraInicio();
        this.horaFin = bloqueo.getHoraFin();
        this.motivo = bloqueo.getMotivo();
        this.estado = bloqueo.getEstado();
    }

    public Long getIdBloqueo() { return idBloqueo; }
    public Long getIdEspecialista() { return idEspecialista; }
    public LocalDate getFecha() { return fecha; }
    public LocalTime getHoraInicio() { return horaInicio; }
    public LocalTime getHoraFin() { return horaFin; }
    public String getMotivo() { return motivo; }
    public String getEstado() { return estado; }
}
