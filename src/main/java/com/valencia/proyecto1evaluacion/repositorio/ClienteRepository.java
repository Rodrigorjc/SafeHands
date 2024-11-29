package com.valencia.proyecto1evaluacion.repositorio;

import com.valencia.proyecto1evaluacion.modelos.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ClienteRepository  extends JpaRepository<Cliente, Integer> {
    @Query("SELECT (u.username, c.id, c.dni, SUM(lp.cantidad * lp.precioUnitario)) " +
            "FROM Cliente c " +
            "JOIN c.usuario u " +
            "JOIN c.pedidos p " +
            "JOIN p.lineaPedido lp " +
            "GROUP BY u.username, c.id, c.dni " +
            "ORDER BY SUM(lp.cantidad * lp.precioUnitario) DESC")
    List<Cliente> findTotalPedidosPorCliente();

    Cliente findClienteByUsuarioId(Integer id);
}
