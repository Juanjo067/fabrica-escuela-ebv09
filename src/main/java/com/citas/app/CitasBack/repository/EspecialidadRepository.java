package com.citas.app.CitasBack.repository;

import com.citas.app.CitasBack.model.Especialidad;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface EspecialidadRepository extends JpaRepository<Especialidad, Long> {

    // Filtra para traer solo las que están activas (Escenario 1)
    List<Especialidad> findByActivoTrue();

    // Busca por ID pero garantizando que esté activa (Escenario 3)
    Optional<Especialidad> findByIdEspecialidadAndActivoTrue(Long idEspecialidad);

}
