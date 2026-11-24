package com.citas.app.CitasBack.repository;

import com.citas.app.CitasBack.model.Sesion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SesionRepository extends JpaRepository<Sesion, Long> {

    Optional<Sesion> findByTokenAndActivaTrue(String token);
}
