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

    /**
     * Esta consulta recupera el total recaudado por cada acontecimiento.
     *
     * - Selecciona el nombre del acontecimiento y la suma del producto de la cantidad y el precio unitario de cada línea de pedido.
     * - Une la entidad LineaPedido con su entidad Producto asociada y luego con la entidad ProveedoresAcontecimiento asociada al producto.
     * - Agrupa los resultados por el nombre del acontecimiento.
     *
     * @return una lista de Object[] que contiene el nombre del acontecimiento y el total recaudado por cada acontecimiento.
     */
    @Query(value = "SELECT a.nombre, SUM(lp.cantidad * lp.precio_unitario) AS total_recaudado " +
            "FROM safe_hand.linea_pedido lp " +
            "JOIN safe_hand.producto prod ON lp.id_producto = prod.id " +
            "JOIN safe_hand.proveedores_acontecimiento pa ON prod.id = pa.id_producto " +
            "JOIN safe_hand.acontecimiento a ON pa.id_acontecimiento = a.id " +
            "GROUP BY a.nombre", nativeQuery = true)
    List<Object[]>findTotalRecaudadoPorAcontecimientoRaw();

}
