package com.citas.app.CitasBack.dto;

import com.citas.app.CitasBack.model.Especialista;

public class EspecialistaResponse extends UsuarioResponse {

    private final String registroMedico;

    public EspecialistaResponse(Especialista especialista) {
        super(especialista); // Mapea idUsuario, nombre, apellido, correo y teléfono
        this.registroMedico = especialista.getRegistroMedico();
    }

    public String getRegistroMedico() { return registroMedico; }
}
