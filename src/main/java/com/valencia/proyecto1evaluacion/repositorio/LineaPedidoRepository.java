package com.valencia.proyecto1evaluacion.repositorio;

import com.valencia.proyecto1evaluacion.modelos.LineaPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LineaPedidoRepository  extends JpaRepository<LineaPedido, Integer> {
    @Query("SELECT SUM(lp.cantidad * lp.precioUnitario) FROM LineaPedido lp")
    Float obtenerTotalDonaciones();

    /**
     * Esta consulta recupera el total recaudado por cada proveedor.
     * - Selecciona el nombre del proveedor y la suma del producto de la cantidad y el precio unitario de cada línea de pedido.
     * - Une la entidad LineaPedido con su entidad Producto asociada y luego con la entidad Proveedores asociada al producto.
     * - Agrupa los resultados por el nombre del proveedor.
     * - Ordena los resultados por la suma del total recaudado en orden descendente.
     *
     * @return una lista de ProveedoresDTO que contiene el nombre del proveedor y el total recaudado por cada proveedor.
     */
    @Query(value = "SELECT p.nombre, SUM(lp.cantidad * lp.precio_unitario) as total_recaudado " +
            "FROM linea_pedido lp " +
            "JOIN producto prod ON lp.id_producto = prod.id " +
            "JOIN proveedores p ON prod.id_proveedores = p.id " +
            "GROUP BY p.nombre", nativeQuery = true)
    List<Object[]> findTotalRecaudadoPorProveedorRaw();

    @Query("SELECT p.nombre, p.url FROM LineaPedido lp " +
            "JOIN Producto p ON lp.producto.id = p.id " +
            "JOIN Pedido ped ON lp.pedido.id = ped.id " +
            "JOIN Cliente c ON ped.cliente.id = c.id " +
            "WHERE c.usuario.id = :userId")
    List<Object[]> findDonatedProductsByUserId(@Param("userId") Integer userId);

    @Query("SELECT a.nombre, a.img FROM LineaPedido lp " +
            "JOIN Acontecimiento a ON lp.acontecimiento.id = a.id " +
            "JOIN Pedido ped ON lp.pedido.id = ped.id " +
            "JOIN Cliente c ON ped.cliente.id = c.id " +
            "WHERE c.usuario.id = :userId")
    List<Object[]> findDonatedEventsByUserId(@Param("userId") Integer userId);
}
