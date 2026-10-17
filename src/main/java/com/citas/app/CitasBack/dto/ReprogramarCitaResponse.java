package com.citas.app.CitasBack.dto;

import com.citas.app.CitasBack.model.Cita;
import com.citas.app.CitasBack.model.ReprogramacionCita;

/** Resultado de la reprogramación: horario nuevo de la cita y horario anterior, que quedó libre. */
public class ReprogramarCitaResponse {

    private final String mensaje;
    private final Long idCita;
    private final String estadoCita;
    private final HorarioCitaResponse horarioAnterior;
    private final HorarioCitaResponse horarioNuevo;

    public ReprogramarCitaResponse(Cita cita, ReprogramacionCita reprogramacion) {
        this.mensaje = "Cita reprogramada exitosamente";
        this.idCita = cita.getIdCita();
        this.estadoCita = cita.getEstadoCita();
        this.horarioAnterior = new HorarioCitaResponse(reprogramacion.getFechaAnterior(), reprogramacion.getHoraAnterior());
        this.horarioNuevo = new HorarioCitaResponse(cita.getFechaCita(), cita.getHoraCita());
    }

    public String getMensaje() { return mensaje; }
    public Long getIdCita() { return idCita; }
    public String getEstadoCita() { return estadoCita; }
    public HorarioCitaResponse getHorarioAnterior() { return horarioAnterior; }
    public HorarioCitaResponse getHorarioNuevo() { return horarioNuevo; }
}
