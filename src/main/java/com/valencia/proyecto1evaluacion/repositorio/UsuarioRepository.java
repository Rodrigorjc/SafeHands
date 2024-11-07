package com.valencia.proyecto1evaluacion.repositorio;

import com.valencia.proyecto1evaluacion.modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UsuarioRepository  extends JpaRepository<Usuario, Integer> {
    @Repository
    interface UsuarioRepositorio extends JpaRepository<Usuario, Integer> {

        Optional<Usuario> findByEmail(String email);


    }
}
