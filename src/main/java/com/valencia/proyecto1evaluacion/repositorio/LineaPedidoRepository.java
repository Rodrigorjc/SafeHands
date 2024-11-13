package com.valencia.proyecto1evaluacion.repositorio;

import com.valencia.proyecto1evaluacion.dtos.ProveedoresDTO;
import com.valencia.proyecto1evaluacion.modelos.LineaPedido;
import com.valencia.proyecto1evaluacion.modelos.Proveedores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LineaPedidoRepository  extends JpaRepository<LineaPedido, Integer> {

    /**
     * Esta consulta recupera el total recaudado por cada proveedor.
     *
     * - Selecciona el nombre del proveedor y la suma del producto de la cantidad y el precio unitario de cada línea de pedido.
     * - Une la entidad LineaPedido con su entidad Producto asociada y luego con la entidad Proveedores asociada al producto.
     * - Agrupa los resultados por el nombre del proveedor.
     * - Ordena los resultados por la suma del total recaudado en orden descendente.
     *
     * @return una lista de ProveedoresDTO que contiene el nombre del proveedor y el total recaudado por cada proveedor.
     */
    @Query("SELECT(p.nombre, SUM(lp.cantidad * lp.precioUnitario)) " +
            "FROM LineaPedido lp " +
            "JOIN lp.producto prod " +
            "JOIN prod.proveedores p " +
            "GROUP BY p.nombre " +
            "ORDER BY SUM(lp.cantidad * lp.precioUnitario) DESC")
    List<ProveedoresDTO> findTotalRecaudadoPorProveedor();


    /**
     * Esta consulta recupera el resumen de las líneas de pedido.
     *
     * - Selecciona el ID del pedido, la suma de las cantidades, el promedio de los precios unitarios y la suma del total de cada línea.
     * - Agrupa los resultados por el ID del pedido.
     *
     * @return una lista de LineaPedidoSummaryDTO que contiene el ID del pedido, la cantidad total, el precio promedio y el total de la línea.
     */
    @Query("SELECT (lp.pedido.id, SUM(lp.cantidad), AVG(lp.precioUnitario), SUM(lp.cantidad * lp.precioUnitario)) " +
            "FROM LineaPedido lp " +
            "GROUP BY lp.pedido.id")
    List<LineaPedido> findLineaPedidoSummary();
}
