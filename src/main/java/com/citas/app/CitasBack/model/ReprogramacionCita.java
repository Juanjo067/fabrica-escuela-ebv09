package com.citas.app.CitasBack.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Historial de reprogramaciones: guarda de qué fecha y hora a cuál se movió una
 * cita. La cita conserva su mismo id; solo cambian su fechaCita y horaCita.
 *
 * Relación: muchas ReprogramacionCita -> una Cita.
 */
@Entity
@Table(name = "reprogramacion_cita")
public class ReprogramacionCita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReprogramacion;

    @ManyToOne
    @JoinColumn(name = "id_cita", nullable = false)
    private Cita cita;

    @Column(nullable = false)
    private LocalDate fechaAnterior;

    @Column(nullable = false)
    private LocalTime horaAnterior;

    @Column(nullable = false)
    private LocalDate fechaNueva;

    @Column(nullable = false)
    private LocalTime horaNueva;

    @Column(length = 200)
    private String motivo;

    @Column(nullable = false)
    private LocalDateTime fechaRegistro;

    public Long getIdReprogramacion() { return idReprogramacion; }
    public void setIdReprogramacion(Long idReprogramacion) { this.idReprogramacion = idReprogramacion; }

    public Cita getCita() { return cita; }
    public void setCita(Cita cita) { this.cita = cita; }

    public LocalDate getFechaAnterior() { return fechaAnterior; }
    public void setFechaAnterior(LocalDate fechaAnterior) { this.fechaAnterior = fechaAnterior; }

    public LocalTime getHoraAnterior() { return horaAnterior; }
    public void setHoraAnterior(LocalTime horaAnterior) { this.horaAnterior = horaAnterior; }

    public LocalDate getFechaNueva() { return fechaNueva; }
    public void setFechaNueva(LocalDate fechaNueva) { this.fechaNueva = fechaNueva; }

    public LocalTime getHoraNueva() { return horaNueva; }
    public void setHoraNueva(LocalTime horaNueva) { this.horaNueva = horaNueva; }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }

    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }
}
