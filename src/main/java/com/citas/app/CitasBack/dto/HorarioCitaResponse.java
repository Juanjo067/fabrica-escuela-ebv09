package com.citas.app.CitasBack.dto;

import java.time.LocalDate;
import java.time.LocalTime;

/** Fecha y hora de una cita. Lo usan HU-14 (horario liberado) y HU-15 (anterior / nuevo). */
public class HorarioCitaResponse {

    private final LocalDate fechaCita;
    private final LocalTime horaCita;

    public HorarioCitaResponse(LocalDate fechaCita, LocalTime horaCita) {
        this.fechaCita = fechaCita;
        this.horaCita = horaCita;
    }

    public LocalDate getFechaCita() { return fechaCita; }
    public LocalTime getHoraCita() { return horaCita; }
}
