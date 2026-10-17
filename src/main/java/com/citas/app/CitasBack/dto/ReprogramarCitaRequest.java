package com.citas.app.CitasBack.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReprogramarCitaRequest {

    // Igual que en HU-14: sin token ni sesión, el id del usuario viaja en el body.
    @NotNull(message = "Debe indicar qué usuario reprograma la cita")
    private Long idUsuario;

    @NotNull(message = "La nueva fecha de la cita es obligatoria")
    private LocalDate nuevaFechaCita;

    @NotNull(message = "La nueva hora de la cita es obligatoria")
    private LocalTime nuevaHoraCita;

    @Size(max = 200, message = "El motivo no puede superar los 200 caracteres")
    private String motivo;

    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }

    public LocalDate getNuevaFechaCita() { return nuevaFechaCita; }
    public void setNuevaFechaCita(LocalDate nuevaFechaCita) { this.nuevaFechaCita = nuevaFechaCita; }

    public LocalTime getNuevaHoraCita() { return nuevaHoraCita; }
    public void setNuevaHoraCita(LocalTime nuevaHoraCita) { this.nuevaHoraCita = nuevaHoraCita; }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
}
