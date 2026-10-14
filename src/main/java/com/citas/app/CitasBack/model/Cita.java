package com.citas.app.CitasBack.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

@Entity
@Table(name = "cita")
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCita;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    @JsonIgnoreProperties({"contrasenaHash", "correo", "telefono"})
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_especialista", nullable = false)
    @JsonIgnoreProperties({"contrasenaHash", "correo", "telefono"})
    private Especialista especialista;

    @ManyToOne
    @JoinColumn(name = "id_especialidad", nullable = false)
    @JsonIgnoreProperties("especialista")
    private Especialidad especialidad;

    private LocalDate fechaCita;
    private LocalTime horaCita;

    @Enumerated(EnumType.STRING)
    private EstadoCita estado = EstadoCita.PROGRAMADA;

    private LocalDateTime fechaCreacion = LocalDateTime.now();

    public enum EstadoCita { PROGRAMADA, CONFIRMADA, CANCELADA, REPROGRAMADA, COMPLETADA }

    public Long getIdCita() { return idCita; }
    public void setIdCita(Long idCita) { this.idCita = idCita; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public Especialista getEspecialista() { return especialista; }
    public void setEspecialista(Especialista especialista) { this.especialista = especialista; }

    public Especialidad getEspecialidad() { return especialidad; }
    public void setEspecialidad(Especialidad especialidad) { this.especialidad = especialidad; }

    public LocalDate getFechaCita() { return fechaCita; }
    public void setFechaCita(LocalDate fechaCita) { this.fechaCita = fechaCita; }

    public LocalTime getHoraCita() { return horaCita; }
    public void setHoraCita(LocalTime horaCita) { this.horaCita = horaCita; }

    public EstadoCita getEstado() { return estado; }
    public void setEstado(EstadoCita estado) { this.estado = estado; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
}
