package com.valencia.proyecto1evaluacion.repositorio;

import com.valencia.proyecto1evaluacion.modelos.Acontecimiento;
import com.valencia.proyecto1evaluacion.modelos.Ong;
import com.valencia.proyecto1evaluacion.modelos.OngAcontecimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OngRepository extends JpaRepository<Ong, Integer> {
    Optional<Ong> findByUsuarioId(Integer usuarioId);

}

