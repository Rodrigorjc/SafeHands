package com.valencia.proyecto1evaluacion.repositorio;

import com.valencia.proyecto1evaluacion.dtos.ProductoDTO;
import com.valencia.proyecto1evaluacion.modelos.Producto;
import com.valencia.proyecto1evaluacion.modelos.ProveedoresAcontecimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProveedoresAcontecimientoRepository extends JpaRepository<ProveedoresAcontecimiento, Integer> {
    public List<ProveedoresAcontecimiento> findByAcontecimientoId(Integer idAcontecimiento);


}
