package com.valencia.proyecto1evaluacion.repositorio;

import com.valencia.proyecto1evaluacion.dtos.ProveedorInfoDTO;
import com.valencia.proyecto1evaluacion.dtos.ProveedorRankingDTO;
import com.valencia.proyecto1evaluacion.dtos.ProveedoresDTO;
import com.valencia.proyecto1evaluacion.modelos.Proveedores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ProveedoresRepository extends JpaRepository<Proveedores, Integer> {
    Optional<Proveedores> findByUsuarioId(Integer usuarioId);

    @Query("SELECT p.id FROM Proveedores p WHERE p.usuario.id = :usuarioId")
    Integer findIdByUsuarioId(@Param("usuarioId") Integer usuarioId);


    @Query("SELECT p FROM Proveedores p WHERE LOWER(p.nombre) LIKE LOWER(CONCAT('%', :busqueda, '%')) " +

            "OR LOWER(p.sede) LIKE LOWER(CONCAT('%', :busqueda, '%'))")
    List<Proveedores> buscar(@Param("busqueda") String busqueda);


    boolean existsByIdAndValidado(Integer id, Boolean validado);
    List<Proveedores> findByValidadoFalse();

    Proveedores findClienteByUsuarioId(Integer id);

    @Query("""
    SELECT new com.valencia.proyecto1evaluacion.dtos.ProveedorRankingDTO(
        p.nombre,
        CAST(SUM(lp.cantidad * lp.precioUnitario) AS double)
    )
    FROM LineaPedido lp
    JOIN lp.producto pr
    JOIN pr.proveedores p
    GROUP BY p.id, p.nombre
    ORDER BY SUM(lp.cantidad * lp.precioUnitario) DESC
""")
    List<ProveedorRankingDTO> obtenerRankingProveedores();

    @Query("""
       SELECT new com.valencia.proyecto1evaluacion.dtos.ProveedorInfoDTO(
           p.nombre,
           p.id,
           p.img,
           CAST(SUM(lp.cantidad * lp.precioUnitario) AS double)
       )
       FROM LineaPedido lp
       JOIN lp.producto pr
       JOIN pr.proveedores p
       GROUP BY p.id, p.nombre, p.img
       ORDER BY SUM(lp.cantidad * lp.precioUnitario) DESC
       """)
    List<ProveedorInfoDTO> obtenerInfoProveedores();

    List<Proveedores> findAllByValidadoIsTrue();


}