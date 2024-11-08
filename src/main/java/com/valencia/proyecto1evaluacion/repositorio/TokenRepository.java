package com.valencia.proyecto1evaluacion.repositorio;

import com.valencia.proyecto1evaluacion.modelos.TokenAcceso;
import com.valencia.proyecto1evaluacion.modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<TokenAcceso,Integer> {
    TokenAcceso findTopByUsuario(Usuario usuario);

}
