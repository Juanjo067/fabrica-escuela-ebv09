package com.citas.app.CitasBack.dto;

public class LoginResponse {

    private final String mensaje;
    private final UsuarioResponse usuario;

    public LoginResponse(UsuarioResponse usuario) {
        this.mensaje = "Inicio de sesion exitoso";
        this.usuario = usuario;
    }

    public String getMensaje() { return mensaje; }
    public UsuarioResponse getUsuario() { return usuario; }
}
