package com.valencia.proyecto1evaluacion.repositorios;

import com.valencia.proyecto1evaluacion.modelos.Acontecimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcontecimientoRepositorio extends JpaRepository<Acontecimiento, Integer> {
}
