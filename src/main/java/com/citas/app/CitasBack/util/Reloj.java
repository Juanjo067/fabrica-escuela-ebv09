package com.citas.app.CitasBack.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Fecha y hora "actual" de la plataforma, fijada en zona horaria de Colombia
 * para que las validaciones de "fecha pasada" y "anticipación mínima" den lo
 * mismo en tu computador y en el servidor del despliegue (normalmente en UTC).
 */
public final class Reloj {

    public static final ZoneId ZONA = ZoneId.of("America/Bogota");

    private Reloj() {
    }

    public static LocalDateTime ahora() {
        return LocalDateTime.now(ZONA);
    }

    public static LocalDate hoy() {
        return LocalDate.now(ZONA);
    }
}
