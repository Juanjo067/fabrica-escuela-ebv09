package com.citas.app.CitasBack.dto;

import com.citas.app.CitasBack.model.Especialista;

public class EspecialistaDetalleResponse extends EspecialistaResponse {
    
    // El constructor llama a super() para mapear id, nombre, apellido, correo, teléfono y registroMédico.
    // Dejamos esta clase lista para agregar la lista de horarios o especialidades en la Task 188.
    public EspecialistaDetalleResponse(Especialista especialista) {
        super(especialista); 
    }
}