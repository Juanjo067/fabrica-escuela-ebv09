package com.citas.app.CitasBack.model;

/**
 * ACTIVO   -> el horario no se puede reservar.
 * LIBERADO -> el especialista lo desbloqueó; se conserva como historial.
 */
public final class EstadoBloqueo {

    public static final String ACTIVO = "ACTIVO";
    public static final String LIBERADO = "LIBERADO";

    private EstadoBloqueo() {
    }
}
