package com.valencia.proyecto1evaluacion.repositorio;

import com.valencia.proyecto1evaluacion.modelos.ProveedoresAcontecimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProveedoresAcontecimientoRepository  extends JpaRepository<ProveedoresAcontecimiento, Integer> {
}
