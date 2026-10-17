package com.citas.app.CitasBack.repository;

import com.citas.app.CitasBack.model.ReprogramacionCita;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReprogramacionCitaRepository extends JpaRepository<ReprogramacionCita, Long> {

    List<ReprogramacionCita> findByCita_IdCitaOrderByFechaRegistroAsc(Long idCita);
}
