package com.citas.app.CitasBack.service;

import com.citas.app.CitasBack.model.Cita;
import com.citas.app.CitasBack.model.EstadoCita;
import com.citas.app.CitasBack.repository.CitaRepository;
import com.citas.app.CitasBack.util.Reloj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
public class CancelacionCitaService {

    @Autowired
    private CitaRepository citaRepository;

    // Anticipación mínima (en horas) para poder cancelar. Se cambia en
    // application.properties con: citas.cancelacion.horas-minimas=24
    @Value("${citas.cancelacion.horas-minimas:24}")
    private long horasMinimas;

    /**
     * HU-14: cancela una cita. No se borra: pasa a estado CANCELADA. HU-12 ya
     * ignora las citas CANCELADA al revisar si un horario está ocupado, así que
     * ese horario queda libre de inmediato.
     */
    @Transactional
    public Cita cancelar(Long idCita, Long idUsuario, String motivo) {

        Cita cita = citaRepository.findById(idCita)
            .orElseThrow(() -> new NoSuchElementException(
                "No existe una cita con id " + idCita
            ));

        // Condiciones para cancelar.
        if (!idUsuario.equals(cita.getUsuario().getIdUsuario())) {
            throw new IllegalArgumentException("La cita no pertenece al usuario indicado");
        }

        if (EstadoCita.CANCELADA.equals(cita.getEstadoCita())) {
            throw new IllegalArgumentException("La cita ya fue cancelada");
        }
        if (!EstadoCita.admiteCambios(cita.getEstadoCita())) {
            throw new IllegalArgumentException("Solo se pueden cancelar citas solicitadas o confirmadas");
        }

        LocalDateTime inicioCita = cita.getFechaCita().atTime(cita.getHoraCita());
        if (Reloj.ahora().plusHours(horasMinimas).isAfter(inicioCita)) {
            throw new IllegalArgumentException(
                "Solo se puede cancelar con al menos " + horasMinimas + " horas de anticipación"
            );
        }

        // Actualización del estado.
        cita.setEstadoCita(EstadoCita.CANCELADA);
        cita.setMotivoCancelacion(motivo == null || motivo.isBlank() ? null : motivo.trim());
        cita.setFechaCancelacion(Reloj.ahora());

        return citaRepository.save(cita);
    }
}
