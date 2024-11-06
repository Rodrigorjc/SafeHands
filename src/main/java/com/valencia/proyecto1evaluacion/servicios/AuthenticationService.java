package com.valencia.proyecto1evaluacion.servicios;


import com.valencia.proyecto1evaluacion.dtos.AuthenticationDTO;
import com.valencia.proyecto1evaluacion.dtos.AuthenticationRequestDTO;
import com.valencia.proyecto1evaluacion.repositorios.UsuarioRepositorio;
import com.valencia.proyecto1evaluacion.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final ObjectProvider<AuthenticationManager> authenticationManagerProvider;
    private final UsuarioRepositorio usuarioRepositorio;
    private final JwtService jwtService;


    public AuthenticationDTO authenticate(AuthenticationRequestDTO dto) {
        AuthenticationManager authenticationManager = authenticationManagerProvider.getObject();
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getEmail(),
                        dto.getPassword()
                )
        );
        var user = usuarioRepositorio.findByEmail(dto.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationDTO.builder().token(jwtToken).build();
    }
}