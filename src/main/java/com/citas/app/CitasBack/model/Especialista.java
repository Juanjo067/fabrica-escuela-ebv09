package com.citas.app.CitasBack.model;

import jakarta.persistence.*;

@Entity
@Table(name = "especialista")
public class Especialista extends Usuario {

    private String registroMedico;

    public String getRegistroMedico() { return registroMedico; }
    public void setRegistroMedico(String registroMedico) { this.registroMedico = registroMedico; }
}
