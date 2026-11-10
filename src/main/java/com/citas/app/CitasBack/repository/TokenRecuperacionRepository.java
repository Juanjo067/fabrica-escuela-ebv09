package com.citas.app.CitasBack.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.citas.app.CitasBack.model.TokenRecuperacion;

public interface TokenRecuperacionRepository extends JpaRepository<TokenRecuperacion, Long> {
    Optional<TokenRecuperacion> findByToken(String token);
}