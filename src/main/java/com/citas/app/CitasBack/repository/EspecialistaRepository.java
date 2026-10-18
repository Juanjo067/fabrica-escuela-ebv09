package com.citas.app.CitasBack.repository;

import com.citas.app.CitasBack.model.Especialista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface EspecialistaRepository extends JpaRepository<Especialista, Long> {

boolean existsByCorreo(String correo);

}
