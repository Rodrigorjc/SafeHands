package com.valencia.proyecto1evaluacion.repositorio;

import com.valencia.proyecto1evaluacion.modelos.Acontecimiento;
import com.valencia.proyecto1evaluacion.modelos.Ong;
import com.valencia.proyecto1evaluacion.modelos.OngAcontecimiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OngAcontecimientoRepository extends JpaRepository<OngAcontecimiento, Integer> {
    List<OngAcontecimiento> findByOngId(Integer ongId);
    boolean existsByOngAndAcontecimiento(Ong ong, Acontecimiento acontecimiento);


}
