package com.citas.app.CitasBack.repository;

import com.citas.app.CitasBack.model.Especialista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

public interface EspecialistaRepository extends JpaRepository<Especialista, Long> {

    boolean existsByCorreo(String correo);

    // HU-20 (Task 188): Listar especialistas activos
    List<Especialista> findByActivoTrue();

    // HU-20 (Task 188): Consultar detalle asegurando que esté activo
    Optional<Especialista> findByIdUsuarioAndActivoTrue(Long idUsuario);

}
