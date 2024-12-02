package com.valencia.proyecto1evaluacion.repositorio;

import com.valencia.proyecto1evaluacion.modelos.Acontecimiento;
import com.valencia.proyecto1evaluacion.modelos.Ong;
import com.valencia.proyecto1evaluacion.modelos.Proveedores;
import com.valencia.proyecto1evaluacion.modelos.OngAcontecimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OngRepository extends JpaRepository<Ong, Integer> {
    Optional<Ong> findByUsuarioId(Integer usuarioId);

    Ong findClienteByUsuarioId(Integer id);
    Optional<Ong> findOngByUsuarioId(Integer id);

//    @Query("SELECT o FROM Ong o WHERE o.usuario.id = :usuarioId")
//    Optional<Ong> findByUsuarioId(@Param("usuarioId") Integer usuarioId);

    Optional<Ong>findByUsuarioUsername(String username);
}

