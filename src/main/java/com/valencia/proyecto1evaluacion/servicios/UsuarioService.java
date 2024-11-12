package com.valencia.proyecto1evaluacion.servicios;


import com.valencia.proyecto1evaluacion.dtos.AuthenticationDTO;
import com.valencia.proyecto1evaluacion.dtos.AuthenticationRequestDTO;
import com.valencia.proyecto1evaluacion.dtos.UsuarioDto;
import com.valencia.proyecto1evaluacion.enums.Rol;
import com.valencia.proyecto1evaluacion.modelos.Proveedores;
import com.valencia.proyecto1evaluacion.modelos.Usuario;
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

@Service
@AllArgsConstructor
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepositorio;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final ProveedoresRepository proveedoresRepositorio;

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

    public AuthenticationDTO register(UsuarioDto userDTO) {
        Usuario usuario = new Usuario();
        usuario.setUsername(userDTO.getUsername());
        usuario.setEmail(userDTO.getEmail());
        usuario.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        usuario.setRol(Rol.CLIENTE);
        usuarioRepositorio.save(usuario);
        var jwtToken = jwtService.generateToken(usuario);
        return AuthenticationDTO.builder().token(jwtToken).build();
    }

//    public AuthenticationDTO authenticate(AuthenticationRequestDTO dto) {
//        return authenticationService.authenticate(dto);
//    }

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


}