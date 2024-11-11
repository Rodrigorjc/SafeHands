//package com.valencia.proyecto1evaluacion.servicios;
//
//
//import com.valencia.proyecto1evaluacion.dtos.AuthenticationDTO;
//import com.valencia.proyecto1evaluacion.dtos.AuthenticationRequestDTO;
//import com.valencia.proyecto1evaluacion.repositorio.UsuarioRepository;
//import com.valencia.proyecto1evaluacion.security.JwtService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.ObjectProvider;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class AuthenticationService {
//
//    private final ObjectProvider<AuthenticationManager> authenticationManagerProvider;
//    private final UsuarioRepository usuarioRepositorio;
//    private final JwtService jwtService;
//
//
//    public AuthenticationDTO authenticate(AuthenticationRequestDTO dto) {
//        AuthenticationManager authenticationManager = authenticationManagerProvider.getObject();
//        authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        dto.getUsername(),
//                        dto.getPassword()
//                )
//        );
//        var user = usuarioRepositorio.findTopByUsername(dto.getUsername());
//        var jwtToken = jwtService.generateToken(user);
//        return AuthenticationDTO.builder().token(jwtToken).build();
//    }
//}