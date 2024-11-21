package com.valencia.proyecto1evaluacion.repositorio;

import com.valencia.proyecto1evaluacion.modelos.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {


    @Query("SELECT p FROM Producto p WHERE "
            + "(p.precio >= :precioMin OR :precioMin IS NULL) "
            + "AND (p.precio <= :precioMax OR :precioMax IS NULL) "
            + "AND (p.proveedores.nombre LIKE %:proveedor% OR :proveedor IS NULL) "
            + "AND (p.nombre LIKE %:nombre% OR :nombre IS NULL)")
    List<Producto> buscarProductos(@Param("precioMin") Double precioMin,
                                   @Param("precioMax") Double precioMax,
                                   @Param("proveedor") String proveedor,
                                   @Param("nombre") String nombre);
}