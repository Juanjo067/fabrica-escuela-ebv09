package com.citas.app.CitasBack.dto;

import com.citas.app.CitasBack.model.Usuario;

public class UsuarioResponse {

    private final Long idUsuario;
    private final String nombre;
    private final String apellido;
    private final String correo;
    private final String telefono;

    public UsuarioResponse(Usuario usuario) {
        this.idUsuario = usuario.getIdUsuario();
        this.nombre = usuario.getNombre();
        this.apellido = usuario.getApellido();
        this.correo = usuario.getCorreo();
        this.telefono = usuario.getTelefono();
    }

    public Long getIdUsuario() { return idUsuario; }
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public String getCorreo() { return correo; }
    public String getTelefono() { return telefono; }
}