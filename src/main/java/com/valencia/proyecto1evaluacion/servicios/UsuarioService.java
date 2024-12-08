package com.valencia.proyecto1evaluacion.servicios;


import com.valencia.proyecto1evaluacion.dtos.AuthenticationDTO;
import com.valencia.proyecto1evaluacion.dtos.UsuarioDto;
import com.valencia.proyecto1evaluacion.enums.Rol;
import com.valencia.proyecto1evaluacion.modelos.Cliente;
import com.valencia.proyecto1evaluacion.modelos.Proveedores;
import com.valencia.proyecto1evaluacion.modelos.TokenAcceso;
import com.valencia.proyecto1evaluacion.modelos.Usuario;
import com.valencia.proyecto1evaluacion.repositorio.ClienteRepository;
import com.valencia.proyecto1evaluacion.repositorio.ProveedoresRepository;
import com.valencia.proyecto1evaluacion.repositorio.UsuarioRepository;
import com.valencia.proyecto1evaluacion.security.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepositorio;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final ProveedoresRepository proveedoresRepositorio;
    private final ClienteRepository clienteRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
            return usuarioRepositorio.findTopByUsername(email)
                    .orElseThrow(()-> new UsernameNotFoundException("Usuario no encontrado"));
    }

    public Usuario buscarUsuarioPorNombre(String username) {
        return usuarioRepositorio.findTopByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
    }

    public Usuario guardarUsuario(UsuarioDto dto){
        Usuario usuario = new Usuario();
        usuario.setEmail(dto.getEmail());
        usuario.setUsername(dto.getUsername());
        usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        usuario.setRol(Rol.CLIENTE);
        return usuarioRepositorio.save(usuario);
    }

    public AuthenticationDTO login(UsuarioDto usuarioDTO) {
        Usuario usuario;
        try {
            usuario = (Usuario) loadUserByUsername(usuarioDTO.getUsername());
        } catch (UsernameNotFoundException e) {
            usuario = null;
        }

        String apiKey = null;
        String mensaje;

        if (usuario == null) {
            mensaje = "Usuario No encontrado";
        } else if (!validarPassword(usuario, usuarioDTO.getPassword())) {
            mensaje = "Contraseña no válida";
        } else {
            if (usuario.getToken() == null || jwtService.isTokenExpired(usuario.getToken().getToken())) {
                apiKey = jwtService.generateToken(usuario, usuario.getId(), usuario.getRol().name());
                TokenAcceso token = usuario.getToken() == null ? new TokenAcceso() : usuario.getToken();
                token.setUsuario(usuario);
                token.setToken(apiKey);
                token.setFechaExpiracion(LocalDateTime.now().plusDays(1));
                tokenService.save(token);
            } else {
                apiKey = usuario.getToken().getToken();
            }
        }

        return AuthenticationDTO.builder().token(apiKey).build();
    }

    public AuthenticationDTO register(UsuarioDto userDTO) {
        Usuario usuario = new Usuario();
        usuario.setUsername(userDTO.getUsername());
        usuario.setEmail(userDTO.getEmail());
        usuario.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        usuario.setRol(Rol.CLIENTE);
        usuarioRepositorio.save(usuario);
        Cliente cliente = new Cliente();
        cliente.setUsuario(usuario);
        cliente.setDni(userDTO.getDni());
        clienteRepository.save(cliente);

        var jwtToken = jwtService.generateToken(usuario, usuario.getId(), usuario.getRol().name());
        return AuthenticationDTO.builder().token(jwtToken).build();
    }


    @Transactional
    public Usuario actualizarRol(Integer id, Rol nuevoRol) {
        Usuario usuario = usuarioRepositorio.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuario.setRol(nuevoRol);
        Usuario updatedUsuario = usuarioRepositorio.save(usuario);

        if (nuevoRol == Rol.PROVEEDOR) {
            Proveedores proveedor = new Proveedores();
            proveedor.setUsuario(updatedUsuario);
            proveedoresRepositorio.save(proveedor);
        }

        return updatedUsuario;
    }

    public boolean validarPassword(Usuario usuario, String passwordSinEncriptar){
        return passwordEncoder.matches(passwordSinEncriptar, usuario.getPassword());
    }

    public Rol obtenerRolPorNombreUsuario(Integer id) {
        Usuario usuario = usuarioRepositorio.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        return usuario.getRol();
    }

    public Usuario getById(Integer id) {
        return usuarioRepositorio.getById(id);
    }

    public void updateUsuario(Usuario usuario) {
        usuarioRepositorio.save(usuario);
    }
}
