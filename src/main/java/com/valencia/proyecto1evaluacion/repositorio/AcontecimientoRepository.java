package com.valencia.proyecto1evaluacion.repositorio;

import com.valencia.proyecto1evaluacion.dtos.AcontecimientoInfoDTO;
import com.valencia.proyecto1evaluacion.modelos.Acontecimiento;
import com.valencia.proyecto1evaluacion.modelos.Ong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AcontecimientoRepository extends JpaRepository<Acontecimiento, Integer> {
//    List<Acontecimiento>findByAcontecimiento(Acontecimiento acontecimiento);

//    List<Acontecimiento> findAcontecimientoByOng(Ong ong);

    @Query("""
    SELECT new com.valencia.proyecto1evaluacion.dtos.AcontecimientoInfoDTO(
        a.id,\s
        a.nombre,\s
        a.img,\s
           CAST(SUM(lp.cantidad * lp.precioUnitario) AS double)
    )
    FROM Acontecimiento a
    LEFT JOIN ProveedoresAcontecimiento pa ON a.id = pa.acontecimiento.id
    LEFT JOIN Producto p ON pa.producto.id = p.id
    LEFT JOIN LineaPedido lp ON lp.producto.id = p.id
    GROUP BY a.id, a.nombre, a.img
""")
    List<AcontecimientoInfoDTO> obtenerTotalesAcontecimientos();

}
