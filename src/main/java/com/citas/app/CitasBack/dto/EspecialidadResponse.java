package com.citas.app.CitasBack.dto;

import com.citas.app.CitasBack.model.Especialidad;

public class EspecialidadResponse {
    private Long idEspecialidad;
    private String nombreEspecialidad;
    private String descripcion;
    private Integer duracionMinutos;

    public EspecialidadResponse(Especialidad especialidad) {
        this.idEspecialidad = especialidad.getIdEspecialidad();
        this.nombreEspecialidad = especialidad.getNombreEspecialidad();
        this.descripcion = especialidad.getDescripcion();
        this.duracionMinutos = especialidad.getDuracionMinutos();
    }

    // Getters
    public Long getIdEspecialidad() { return idEspecialidad; }
    public String getNombreEspecialidad() { return nombreEspecialidad; }
    public String getDescripcion() { return descripcion; }
    public Integer getDuracionMinutos() { return duracionMinutos; }
}