package com.valencia.proyecto1evaluacion.repositorio;

import com.valencia.proyecto1evaluacion.modelos.Acontecimiento;
import com.valencia.proyecto1evaluacion.modelos.Producto;
import com.valencia.proyecto1evaluacion.dtos.ProductoDTO;
import com.valencia.proyecto1evaluacion.modelos.Producto;
import com.valencia.proyecto1evaluacion.modelos.ProveedoresAcontecimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProveedoresAcontecimientoRepository  extends JpaRepository<ProveedoresAcontecimiento, Integer> {
    boolean existsByProductoAndAcontecimiento(Producto producto, Acontecimiento acontecimiento);

    public List<ProveedoresAcontecimiento> findByAcontecimientoId(Integer idAcontecimiento);

    @Query("SELECT pa.producto FROM ProveedoresAcontecimiento pa WHERE pa.acontecimiento.id = :acontecimientoId")
    List<Producto> findProductosByAcontecimientoId(@Param("acontecimientoId") Integer acontecimientoId);
}
