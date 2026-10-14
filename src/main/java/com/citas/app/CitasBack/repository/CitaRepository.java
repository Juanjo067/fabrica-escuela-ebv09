package com.citas.app.CitasBack.repository;

import com.citas.app.CitasBack.model.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CitaRepository extends JpaRepository<Cita, Long> {

    // HU-13: citas de un usuario (lista vacía si no tiene)
    List<Cita> findByUsuarioIdUsuario(Long idUsuario);
}
