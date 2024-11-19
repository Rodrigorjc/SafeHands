package com.valencia.proyecto1evaluacion.repositorio;

import com.valencia.proyecto1evaluacion.modelos.Ong;
import com.valencia.proyecto1evaluacion.modelos.Proveedores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OngRepository extends JpaRepository<Ong, Integer> {
    Ong findClienteByUsuarioId(Integer id);
}
