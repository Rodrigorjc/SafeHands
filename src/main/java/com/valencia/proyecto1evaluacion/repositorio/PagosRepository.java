package com.valencia.proyecto1evaluacion.repositorio;

import com.valencia.proyecto1evaluacion.modelos.Pagos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PagosRepository  extends JpaRepository<Pagos, Integer> {


    /**
     * Esta consulta recupera el total de donaciones.
     *
     * - Selecciona la suma del producto de la cantidad y el precio unitario de cada línea de pedido.
     *
     * @return el total de donaciones.
     */
    @Query(value = "SELECT SUM(lp.cantidad * lp.precio_unitario)" + " AS total_donaciones" +
            " FROM  safe_hand.linea_pedido lp", nativeQuery = true)
    Double findTotalDonaciones();
}
