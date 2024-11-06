package com.valencia.proyecto1evaluacion.repositorio;

import com.valencia.proyecto1evaluacion.modelos.Acontecimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcontecimientoRepository extends JpaRepository<Acontecimiento, Integer> {

}