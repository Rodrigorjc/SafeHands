package com.valencia.proyecto1evaluacion.repositorio;

import com.valencia.proyecto1evaluacion.modelos.Acontecimiento;
import com.valencia.proyecto1evaluacion.modelos.Ong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AcontecimientoRepository extends JpaRepository<Acontecimiento, Integer> {
//    List<Acontecimiento>findByAcontecimiento(Acontecimiento acontecimiento);

    List<Acontecimiento> findAcontecimientoByOng(Ong ong);
}
