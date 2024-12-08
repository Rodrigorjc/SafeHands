package com.valencia.proyecto1evaluacion.repositorio;

import com.valencia.proyecto1evaluacion.modelos.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {



}
