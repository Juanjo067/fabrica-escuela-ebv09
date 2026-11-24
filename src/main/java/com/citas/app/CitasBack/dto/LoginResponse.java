package com.citas.app.CitasBack.dto;

public class LoginResponse {

    private final String mensaje;
    private final UsuarioResponse usuario;
    private final String token;

    public LoginResponse(UsuarioResponse usuario, String token) {
        this.mensaje = "Inicio de sesion exitoso";
        this.usuario = usuario;
        this.token = token;
    }

    public String getMensaje() { return mensaje; }
    public UsuarioResponse getUsuario() { return usuario; }
    public String getToken() { return token; }
}
