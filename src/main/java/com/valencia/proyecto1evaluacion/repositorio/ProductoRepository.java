package com.valencia.proyecto1evaluacion.repositorio;

import com.valencia.proyecto1evaluacion.modelos.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {


    @Query("SELECT p FROM Producto p WHERE " +
            "(:precioMin IS NULL OR p.precio >= :precioMin) AND " +
            "(:precioMax IS NULL OR p.precio <= :precioMax) AND " +
            "(:proveedor IS NULL OR p.proveedores.id = :proveedor) AND " +
            "(:nombre IS NULL OR LOWER(p.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')))")
    List<Producto> buscarProductos(@Param("precioMin") Double precioMin,
                                   @Param("precioMax") Double precioMax,
                                   @Param("proveedor") Integer proveedor,
                                   @Param("nombre") String nombre);

    List<Producto> findByProveedoresId(Integer proveedorId);

    List<Producto> findByProveedoresUsuarioId(Integer usuarioId);



    List<Producto> findByAcontecimiento_Id(Integer id);

}