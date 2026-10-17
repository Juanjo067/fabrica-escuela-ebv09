package com.citas.app.CitasBack.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Bloqueo puntual de un rango de horas de un especialista en una fecha
 * (reunión, permiso, calamidad...). Mientras esté ACTIVO, el especialista no
 * puede recibir citas dentro de ese rango.
 *
 * Relación: muchos BloqueoHorario -> un Especialista (igual que
 * DisponibilidadHorario de HU-09).
 */
@Entity
@Table(name = "bloqueo_horario")
public class BloqueoHorario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idBloqueo;

    @ManyToOne
    @JoinColumn(name = "id_especialista", nullable = false)
    private Especialista especialista;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(nullable = false)
    private LocalTime horaInicio;

    @Column(nullable = false)
    private LocalTime horaFin;

    @Column(length = 200)
    private String motivo;

    @Column(nullable = false, length = 20)
    private String estado = EstadoBloqueo.ACTIVO;

    @Column(nullable = false)
    private LocalDateTime fechaCreacion;

    private LocalDateTime fechaLiberacion;

    public Long getIdBloqueo() { return idBloqueo; }
    public void setIdBloqueo(Long idBloqueo) { this.idBloqueo = idBloqueo; }

    public Especialista getEspecialista() { return especialista; }
    public void setEspecialista(Especialista especialista) { this.especialista = especialista; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }

    public LocalTime getHoraFin() { return horaFin; }
    public void setHoraFin(LocalTime horaFin) { this.horaFin = horaFin; }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public LocalDateTime getFechaLiberacion() { return fechaLiberacion; }
    public void setFechaLiberacion(LocalDateTime fechaLiberacion) { this.fechaLiberacion = fechaLiberacion; }
}
