package com.citas.app.CitasBack.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.citas.app.CitasBack.model.EventoSeguridad;

public interface EventoSeguridadRepository extends JpaRepository<EventoSeguridad, Long> {
}