package com.valencia.proyecto1evaluacion.controladores;


import com.valencia.proyecto1evaluacion.dtos.AuthenticationDTO;
import com.valencia.proyecto1evaluacion.dtos.AuthenticationRequestDTO;
import com.valencia.proyecto1evaluacion.dtos.UsuarioDto;
import com.valencia.proyecto1evaluacion.enums.Rol;
import com.valencia.proyecto1evaluacion.modelos.TokenAcceso;
import com.valencia.proyecto1evaluacion.modelos.Usuario;
import com.valencia.proyecto1evaluacion.security.JwtService;
import com.valencia.proyecto1evaluacion.servicios.TokenService;
import com.valencia.proyecto1evaluacion.servicios.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioControler {

    private final UsuarioService usuarioService;
    private final JwtService jwtService;
    private final TokenService tokenService;

    @PostMapping("/register")
    public AuthenticationDTO register(@RequestBody UsuarioDto userDTO) {
        return usuarioService.register(userDTO);
    }

//    @PostMapping("/authenticate")
//    public AuthenticationDTO authenticate(@RequestBody AuthenticationRequestDTO dto) {
//        return usuarioService.authenticate(dto);
//    }

    @PostMapping("/login")
    public AuthenticationDTO login(@RequestBody UsuarioDto usuarioDTO) {
        Usuario usuario;
        try {
            usuario = (Usuario) usuarioService.loadUserByUsername(usuarioDTO.getUsername());
        } catch (UsernameNotFoundException e) {
            usuario = null;
        }

        String apiKey = null;
        String mensaje;

        if (usuario == null) {
            mensaje = "Usuario No encontrado";
        } else if (!usuarioService.validarPassword(usuario, usuarioDTO.getPassword())) {
            mensaje = "Contraseña no válida";
        } else {
            mensaje = "Usuario Logueado";
            // Usuario sin token
            if (usuario.getToken() == null) {
                apiKey = jwtService.generateToken(usuario);
                TokenAcceso token = new TokenAcceso();
                token.setUsuario(usuario);
                token.setToken(apiKey);
                token.setFechaExpiracion(LocalDateTime.now().plusDays(1));
                tokenService.save(token);

                // Usuario con token caducado
            } else if (usuario.getToken().getFechaExpiracion().isBefore(LocalDateTime.now())) {
                TokenAcceso token = usuario.getToken();
                apiKey = jwtService.generateToken(usuario);
                token.setToken(apiKey);
                token.setFechaExpiracion(LocalDateTime.now().plusDays(1));
                tokenService.save(token);

                // Usuario con token válido
            } else {
                apiKey = usuario.getToken().getToken();
            }
        }


        return AuthenticationDTO
                .builder()
                .token(apiKey)
                .build();
    }

    @PutMapping("/actualizarRol/{id}")
    public ResponseEntity<Usuario> actualizarRol(@PathVariable Integer id, @RequestParam Rol nuevoRol) {
        Usuario usuarioActualizado = usuarioService.actualizarRol(id, nuevoRol);
        return ResponseEntity.ok(usuarioActualizado);
    }
}

