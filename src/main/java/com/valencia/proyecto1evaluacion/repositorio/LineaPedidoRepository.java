package com.valencia.proyecto1evaluacion.repositorio;

import com.valencia.proyecto1evaluacion.modelos.LineaPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LineaPedidoRepository  extends JpaRepository<LineaPedido, Integer> {
    @Query("SELECT SUM(lp.cantidad * lp.precioUnitario) FROM LineaPedido lp")
    Float obtenerTotalDonaciones();
}
