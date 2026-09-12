package com.citas.app.CitasBack.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class EspecialidadRequest {

    @NotBlank(message = "El nombre de la especialidad es obligatorio")
    private String nombreEspecialidad;

    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;

    @NotNull(message = "La duración es obligatoria")
    @Positive(message = "La duración debe ser un número positivo")
    private Integer duracionMinutos;

    @NotNull(message = "Debe indicar qué especialista registra esta especialidad")
    private Long idEspecialista;

    public String getNombreEspecialidad() { return nombreEspecialidad; }
    public void setNombreEspecialidad(String nombreEspecialidad) { this.nombreEspecialidad = nombreEspecialidad; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Integer getDuracionMinutos() { return duracionMinutos; }
    public void setDuracionMinutos(Integer duracionMinutos) { this.duracionMinutos = duracionMinutos; }

    public Long getIdEspecialista() { return idEspecialista; }
    public void setIdEspecialista(Long idEspecialista) { this.idEspecialista = idEspecialista; }
}
