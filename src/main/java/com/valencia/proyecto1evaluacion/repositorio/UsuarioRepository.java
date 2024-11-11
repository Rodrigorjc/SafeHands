package com.valencia.proyecto1evaluacion.repositorio;

import com.valencia.proyecto1evaluacion.modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository  extends JpaRepository<Usuario, Integer> {
    Usuario findByEmail(String email);

    Optional<Usuario> findTopByUsername(String username);
    Optional<Usuario> findFirstByUsername(String username);


    @Query("SELECT u FROM Usuario u JOIN Proveedores p ON u.id = p.usuario.id WHERE u.email = :email")
    Optional<Usuario> findByEmailInProveedores(@Param("email") String email);

}
