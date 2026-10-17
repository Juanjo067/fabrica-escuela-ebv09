package com.citas.app.CitasBack.model;

/**
 * Estados de una cita (columna estadoCita de la tabla cita).
 * HU-12 crea las citas en SOLICITADA; HU-14 las pasa a CANCELADA.
 */
public final class EstadoCita {

    public static final String SOLICITADA = "SOLICITADA";
    public static final String CONFIRMADA = "CONFIRMADA";
    public static final String CANCELADA = "CANCELADA";
    public static final String COMPLETADA = "COMPLETADA";

    private EstadoCita() {
    }

    /** Una cita solo se puede cancelar o reprogramar mientras siga pendiente. */
    public static boolean admiteCambios(String estado) {
        return SOLICITADA.equals(estado) || CONFIRMADA.equals(estado);
    }
}
