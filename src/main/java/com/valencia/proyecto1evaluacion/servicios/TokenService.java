package com.valencia.proyecto1evaluacion.servicios;

import com.valencia.proyecto1evaluacion.modelos.TokenAcceso;
import com.valencia.proyecto1evaluacion.modelos.Usuario;
import com.valencia.proyecto1evaluacion.repositorio.TokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TokenService {

    private TokenRepository tokenRepositorio;


    public TokenAcceso getByUsuario(Usuario usuario){
        return tokenRepositorio.findTopByUsuario(usuario);
    }

    public TokenAcceso save(TokenAcceso token){
        return tokenRepositorio.save(token);
    }
}
