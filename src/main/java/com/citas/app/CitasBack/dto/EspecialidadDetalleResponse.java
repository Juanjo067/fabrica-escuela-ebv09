package com.citas.app.CitasBack.dto;

import com.citas.app.CitasBack.model.Especialidad;

public class EspecialidadDetalleResponse extends EspecialidadResponse {
    
    private String nombreEspecialista;
    private String correoEspecialista;

    public EspecialidadDetalleResponse(Especialidad especialidad) {
        super(especialidad);
        // con esto evitamos devolver toda la entidad Especialista, solo datos públicos
        this.nombreEspecialista = especialidad.getEspecialista().getNombre() + " " + especialidad.getEspecialista().getApellido();
        this.correoEspecialista = especialidad.getEspecialista().getCorreo();
    }

    // Getters
    public String getNombreEspecialista() { return nombreEspecialista; }
    public String getCorreoEspecialista() { return correoEspecialista; }
}