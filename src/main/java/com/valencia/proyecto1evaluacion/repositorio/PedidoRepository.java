package com.valencia.proyecto1evaluacion.repositorio;

import com.valencia.proyecto1evaluacion.modelos.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    @Query("SELECT (p.id, SUM(lp.cantidad * lp.precioUnitario)) " +
            "FROM Pedido p " +
            "JOIN p.lineaPedido lp " +
            "GROUP BY p.id " +
            "ORDER BY SUM(lp.cantidad * lp.precioUnitario) DESC")
    List<Pedido> findTotalPedidos();

}
