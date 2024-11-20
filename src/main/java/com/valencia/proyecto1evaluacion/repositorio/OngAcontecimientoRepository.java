package com.valencia.proyecto1evaluacion.repositorio;

import com.valencia.proyecto1evaluacion.modelos.OngAcontecimiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OngAcontecimientoRepository extends JpaRepository<OngAcontecimiento, Integer> {
    List<OngAcontecimiento> findByOngId(Integer ongId);

}
