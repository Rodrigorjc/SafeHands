package com.valencia.proyecto1evaluacion.controladores;


import com.valencia.proyecto1evaluacion.dtos.AuthenticationDTO;
import com.valencia.proyecto1evaluacion.dtos.UsuarioDto;
import com.valencia.proyecto1evaluacion.enums.Rol;
import com.valencia.proyecto1evaluacion.modelos.Usuario;
import com.valencia.proyecto1evaluacion.servicios.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioControler {

    private final UsuarioService usuarioService;

    @PostMapping("/register")
    public AuthenticationDTO register(@RequestBody UsuarioDto userDTO) {
        return usuarioService.register(userDTO);
    }

        private static final Logger logger = LoggerFactory.getLogger(UsuarioControler.class);

    @PostMapping("/login")
    public AuthenticationDTO login(@RequestBody UsuarioDto usuarioDTO) {
        logger.debug("Login method called with usuarioDTO: {}", usuarioDTO);
        return usuarioService.login(usuarioDTO);
    }

    @PutMapping("/actualizarRol/{id}")
    public ResponseEntity<Usuario> actualizarRol(@PathVariable Integer id, @RequestParam Rol nuevoRol) {
        Usuario usuarioActualizado = usuarioService.actualizarRol(id, nuevoRol);
        return ResponseEntity.ok(usuarioActualizado);
    }

    @GetMapping("/rol/{id}")
    public ResponseEntity<Rol> obtenerRolPorNombreUsuario(@PathVariable Integer id) {
        Rol rol = usuarioService.obtenerRolPorNombreUsuario(id);
        return ResponseEntity.ok(rol);
    }
}