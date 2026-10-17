package com.citas.app.CitasBack.dto;

import com.citas.app.CitasBack.model.Cita;

import java.time.LocalDateTime;

/** Resultado de la cancelación: la cita ya cancelada y el horario que quedó libre. */
public class CancelarCitaResponse {

    private final String mensaje;
    private final Long idCita;
    private final String estadoCita;
    private final String motivo;
    private final LocalDateTime fechaCancelacion;
    private final HorarioCitaResponse horarioLiberado;

    public CancelarCitaResponse(Cita cita) {
        this.mensaje = "Cita cancelada exitosamente";
        this.idCita = cita.getIdCita();
        this.estadoCita = cita.getEstadoCita();
        this.motivo = cita.getMotivoCancelacion();
        this.fechaCancelacion = cita.getFechaCancelacion();
        this.horarioLiberado = new HorarioCitaResponse(cita.getFechaCita(), cita.getHoraCita());
    }

    public String getMensaje() { return mensaje; }
    public Long getIdCita() { return idCita; }
    public String getEstadoCita() { return estadoCita; }
    public String getMotivo() { return motivo; }
    public LocalDateTime getFechaCancelacion() { return fechaCancelacion; }
    public HorarioCitaResponse getHorarioLiberado() { return horarioLiberado; }
}
