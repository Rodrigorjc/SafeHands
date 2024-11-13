package com.valencia.proyecto1evaluacion.repositorio;

import com.valencia.proyecto1evaluacion.modelos.Proveedores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import java.util.List;


@Repository
public interface ProveedoresRepository  extends JpaRepository<Proveedores, Integer> {
    Optional<Proveedores> findByUsuarioId(Integer usuarioId);

    @Query("SELECT p FROM Proveedores p WHERE LOWER(p.nombre) LIKE LOWER(CONCAT('%', :busqueda, '%')) " +
            "OR LOWER(p.descripcion) LIKE LOWER(CONCAT('%', :busqueda, '%')) " +
            "OR LOWER(p.sede) LIKE LOWER(CONCAT('%', :busqueda, '%'))")
    List<Proveedores> buscar(@Param("busqueda") String busqueda);



}