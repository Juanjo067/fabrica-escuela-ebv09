package com.citas.app.CitasBack.dto;

import jakarta.validation.constraints.NotBlank;

public class EspecialistaRequest extends UsuarioRequest {

    @NotBlank(message = "El registro médico es obligatorio")
    private String registroMedico;

    public String getRegistroMedico() { return registroMedico; }
    public void setRegistroMedico(String registroMedico) { this.registroMedico = registroMedico; }
}
