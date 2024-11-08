package com.valencia.proyecto1evaluacion.repositorio;

import com.valencia.proyecto1evaluacion.modelos.Proveedores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ProveedoresRepository  extends JpaRepository<Proveedores, Integer> {
    Optional<Proveedores> findByUsuarioId(Integer usuarioId);

}