package com.valencia.proyecto1evaluacion.servicios;

import com.valencia.proyecto1evaluacion.dtos.AuthenticationDTO;
import com.valencia.proyecto1evaluacion.dtos.UsuarioDto;
import com.valencia.proyecto1evaluacion.enums.Rol;
import com.valencia.proyecto1evaluacion.modelos.Ong;
import com.valencia.proyecto1evaluacion.modelos.Usuario;
import com.valencia.proyecto1evaluacion.repositorio.OngRepository;
import com.valencia.proyecto1evaluacion.repositorio.UsuarioRepository;
import com.valencia.proyecto1evaluacion.security.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdminService {

    private final UsuarioRepository usuarioRepositorio;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final OngRepository ongRepositorio;
    private final UsuarioService usuarioService;

    /**
     * Registra un nuevo administrador
     * @param userDTO
     * @return
     */

    public AuthenticationDTO registerAdmin(UsuarioDto userDTO) {
        Usuario usuario = new Usuario();
        usuario.setUsername(userDTO.getUsername());
        usuario.setEmail(userDTO.getEmail());
        usuario.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        usuario.setRol(Rol.ADMIN);
        usuarioRepositorio.save(usuario);

        var jwtToken = jwtService.generateToken(usuario, usuario.getId(), usuario.getRol().name());
        return AuthenticationDTO.builder().token(jwtToken).build();
    }

    /**
     * Elimina una ONG siendo administrador
     * @param ongId
     */
    public void eliminarOng(Integer ongId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nombre = authentication.getName();
        Usuario usuario = usuarioService.buscarUsuarioPorNombre(nombre);

        if (usuario == null || !usuario.getRol().equals(Rol.ADMIN)) {
            throw new SecurityException("No tienes permiso para eliminar ONGs");
        }

        Ong ong = ongRepositorio.findById(ongId)
                .orElseThrow(() -> new RuntimeException("ONG no encontrada"));

        ongRepositorio.delete(ong);
    }

    /**
     * Edita una ONG siendo administrador
     * @param ongId
     * @param updatedOng
     */
    public  Ong editarOng(Integer ongId, Ong updatedOng) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nombre = authentication.getName();
        Usuario usuario = usuarioService.buscarUsuarioPorNombre(nombre);

        if (usuario == null || !usuario.getRol().equals(Rol.ADMIN)) {
            throw new SecurityException("No tienes permiso para editar ONGs");
        }

        Ong ong = ongRepositorio.findById(ongId)
                .orElseThrow(() -> new RuntimeException("ONG no encontrada"));

        ong.setNumVoluntarios(updatedOng.getNumVoluntarios());
        ong.setSede(updatedOng.getSede());
        ong.setDescripcion(updatedOng.getDescripcion());
        ong.setUbicacion(updatedOng.getUbicacion());
        ong.setImg(updatedOng.getImg());

        return ongRepositorio.save(ong);
    }


    /**
     *
     */


}
