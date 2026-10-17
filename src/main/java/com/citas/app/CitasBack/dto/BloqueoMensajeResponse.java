package com.citas.app.CitasBack.dto;

/** Respuesta de bloquear / desbloquear: un mensaje y el bloqueo resultante. */
public class BloqueoMensajeResponse {

    private final String mensaje;
    private final BloqueoResponse bloqueo;

    public BloqueoMensajeResponse(String mensaje, BloqueoResponse bloqueo) {
        this.mensaje = mensaje;
        this.bloqueo = bloqueo;
    }

    public String getMensaje() { return mensaje; }
    public BloqueoResponse getBloqueo() { return bloqueo; }
}
