package com.citas.app.CitasBack.service;

import com.citas.app.CitasBack.dto.ReprogramarCitaRequest;
import com.citas.app.CitasBack.model.Cita;
import com.citas.app.CitasBack.model.BloqueoHorario;
import com.citas.app.CitasBack.model.EstadoBloqueo;
import com.citas.app.CitasBack.model.EstadoCita;
import com.citas.app.CitasBack.model.ReprogramacionCita;
import com.citas.app.CitasBack.repository.BloqueoHorarioRepository;
import com.citas.app.CitasBack.repository.CitaRepository;
import com.citas.app.CitasBack.repository.ReprogramacionCitaRepository;
import com.citas.app.CitasBack.util.Reloj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ReprogramacionCitaService {

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private ReprogramacionCitaRepository reprogramacionCitaRepository;

    @Autowired
    private BloqueoHorarioRepository bloqueoHorarioRepository;

    // Anticipación mínima (en horas) para poder reprogramar. Se cambia en
    // application.properties con: citas.reprogramacion.horas-minimas=24
    @Value("${citas.reprogramacion.horas-minimas:24}")
    private long horasMinimas;

    /**
     * HU-15: mueve una cita a otra fecha y hora. La cita conserva su id y su
     * estado; el horario anterior queda libre porque la cita ya no lo ocupa, y el
     * cambio se registra en reprogramacion_cita.
     */
    @Transactional
    public ReprogramacionCita reprogramar(Long idCita, ReprogramarCitaRequest request) {

        Cita cita = citaRepository.findById(idCita)
            .orElseThrow(() -> new NoSuchElementException(
                "No existe una cita con id " + idCita
            ));

        // Condiciones sobre la cita original.
        if (!request.getIdUsuario().equals(cita.getUsuario().getIdUsuario())) {
            throw new IllegalArgumentException("La cita no pertenece al usuario indicado");
        }
        if (!EstadoCita.admiteCambios(cita.getEstadoCita())) {
            throw new IllegalArgumentException("Solo se pueden reprogramar citas solicitadas o confirmadas");
        }

        LocalDateTime inicioActual = cita.getFechaCita().atTime(cita.getHoraCita());
        if (Reloj.ahora().plusHours(horasMinimas).isAfter(inicioActual)) {
            throw new IllegalArgumentException(
                "Solo se puede reprogramar con al menos " + horasMinimas + " horas de anticipación"
            );
        }

        // Validación del nuevo horario.
        LocalDateTime inicioNuevo = request.getNuevaFechaCita().atTime(request.getNuevaHoraCita());
        if (!inicioNuevo.isAfter(Reloj.ahora())) {
            throw new IllegalArgumentException("La nueva fecha y hora deben ser posteriores al momento actual");
        }
        if (request.getNuevaFechaCita().equals(cita.getFechaCita())
            && request.getNuevaHoraCita().equals(cita.getHoraCita())) {
            throw new IllegalArgumentException("El nuevo horario es igual al horario actual de la cita");
        }

        Long idEspecialista = cita.getEspecialista().getIdUsuario();

        // TODO: volver a validar que el nuevo horario caiga dentro de la
        // disponibilidad activa del especialista (HU-09: DisponibilidadHorario y
        // DisponibilidadHorarioRepository). Quitado temporalmente porque esa HU
        // todavía no está en el proyecto.

        // 1) No debe estar ocupado por otra cita activa (de cualquier usuario).
        boolean ocupado = citaRepository
            .findByEspecialista_IdUsuarioAndFechaCitaAndHoraCitaAndEstadoCitaNot(
                idEspecialista, request.getNuevaFechaCita(), request.getNuevaHoraCita(), EstadoCita.CANCELADA)
            .isPresent();
        if (ocupado) {
            throw new IllegalArgumentException("El nuevo horario ya está ocupado por otra cita");
        }

        // 2) No debe estar bloqueado por el especialista (HU-11).
        List<BloqueoHorario> bloqueos = bloqueoHorarioRepository
            .findByEspecialista_IdUsuarioAndFechaAndEstado(idEspecialista, request.getNuevaFechaCita(), EstadoBloqueo.ACTIVO);

        boolean bloqueado = bloqueos.stream().anyMatch(b ->
            !request.getNuevaHoraCita().isBefore(b.getHoraInicio()) && request.getNuevaHoraCita().isBefore(b.getHoraFin())
        );
        if (bloqueado) {
            throw new IllegalArgumentException("El nuevo horario está bloqueado por el especialista");
        }

        // Registro del historial y actualización de la cita (una sola transacción).
        ReprogramacionCita reprogramacion = new ReprogramacionCita();
        reprogramacion.setCita(cita);
        reprogramacion.setFechaAnterior(cita.getFechaCita());
        reprogramacion.setHoraAnterior(cita.getHoraCita());
        reprogramacion.setFechaNueva(request.getNuevaFechaCita());
        reprogramacion.setHoraNueva(request.getNuevaHoraCita());
        reprogramacion.setMotivo(request.getMotivo() == null || request.getMotivo().isBlank()
            ? null : request.getMotivo().trim());
        reprogramacion.setFechaRegistro(Reloj.ahora());

        cita.setFechaCita(request.getNuevaFechaCita());
        cita.setHoraCita(request.getNuevaHoraCita());
        citaRepository.save(cita);

        return reprogramacionCitaRepository.save(reprogramacion);
    }
}