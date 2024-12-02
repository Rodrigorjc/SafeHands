package com.valencia.proyecto1evaluacion.servicios;

import com.valencia.proyecto1evaluacion.dtos.AuthenticationDTO;
import com.valencia.proyecto1evaluacion.dtos.OngDTO;
import com.valencia.proyecto1evaluacion.dtos.ProveedoresDTO;
import com.valencia.proyecto1evaluacion.dtos.UsuarioDto;
import com.valencia.proyecto1evaluacion.enums.Rol;
import com.valencia.proyecto1evaluacion.modelos.Ong;
import com.valencia.proyecto1evaluacion.modelos.Proveedores;
import com.valencia.proyecto1evaluacion.modelos.Usuario;
import com.valencia.proyecto1evaluacion.repositorio.OngRepository;
import com.valencia.proyecto1evaluacion.repositorio.ProveedoresRepository;
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
    private final ProveedoresRepository proveedoresRepositorio;

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
     */
    public OngDTO editarOng(Integer ongId, OngDTO ongDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nombre = authentication.getName();
        Usuario usuario = usuarioService.buscarUsuarioPorNombre(nombre);

        if (usuario == null || !usuario.getRol().equals(Rol.ADMIN)) {
            throw new SecurityException("No tienes permiso para editar ONGs");
        }

        Ong entity = ongRepositorio.findById(ongId)
                .orElseThrow(() -> new RuntimeException("ONG no encontrada"));

        entity.setNumVoluntarios(ongDto.getNumVoluntarios());
        entity.setSede(ongDto.getSede());
        entity.setDescripcion(ongDto.getDescripcion());
        entity.setUbicacion(ongDto.getUbicacion());
        entity.setImg(ongDto.getImg());

        Usuario usuarioOng = usuarioRepositorio.findById(entity.getUsuario().getId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuarioOng.setEmail(ongDto.getEmail());
        usuarioOng.setUsername(ongDto.getUsername());
        if (ongDto.getPassword() != null && !ongDto.getPassword().isEmpty()) {
            usuarioOng.setPassword(passwordEncoder.encode(ongDto.getPassword()));
        }
        usuarioOng.setRol(Rol.ONG);
        Usuario savedUsuario = usuarioRepositorio.save(usuarioOng);
        entity.setUsuario(savedUsuario);

        Ong savedOng = ongRepositorio.save(entity);

        OngDTO resultDto = new OngDTO();
        resultDto.setId(savedOng.getId());
        resultDto.setNumVoluntarios(savedOng.getNumVoluntarios());
        resultDto.setSede(savedOng.getSede());
        resultDto.setDescripcion(savedOng.getDescripcion());
        resultDto.setUbicacion(savedOng.getUbicacion());
        resultDto.setImg(savedOng.getImg());
        resultDto.setIdUsuario(savedOng.getUsuario().getId());
        resultDto.setEmail(savedOng.getUsuario().getEmail());
        resultDto.setUsername(savedOng.getUsuario().getUsername());

        return resultDto;
    }
    /**
     * Registra un nuevo proveedor siendo administrador
     * @param proveedorDto
     * @return
     */
    public ProveedoresDTO editarProveedor(Integer id, ProveedoresDTO proveedorDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nombre = authentication.getName();
        Usuario usuario = usuarioService.buscarUsuarioPorNombre(nombre);

        if (usuario == null || !usuario.getRol().equals(Rol.ADMIN)) {
            throw new SecurityException("No tienes permiso para editar proveedores");
        }

        Proveedores proveedor = proveedoresRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));

        proveedor.setNombre(proveedorDto.getNombre());
        proveedor.setValidado(proveedorDto.getValidado());
        proveedor.setCif(proveedorDto.getCif());
        proveedor.setNumVoluntarios(proveedorDto.getNumVoluntarios());
        proveedor.setSede(proveedorDto.getSede());
        proveedor.setUbicacion(proveedorDto.getUbicacion());
        proveedor.setImg(proveedorDto.getImg());

        Usuario usuarioProveedor = usuarioRepositorio.findById(proveedor.getUsuario().getId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuarioProveedor.setEmail(proveedorDto.getEmail());
        usuarioProveedor.setUsername(proveedorDto.getUsername());
        if (proveedorDto.getPassword() != null && !proveedorDto.getPassword().isEmpty()) {
            usuarioProveedor.setPassword(passwordEncoder.encode(proveedorDto.getPassword()));
        }
        usuarioProveedor.setRol(Rol.PROVEEDOR);
        Usuario savedUsuario = usuarioRepositorio.save(usuarioProveedor);
        proveedor.setUsuario(savedUsuario);


        Proveedores updatedProveedor = proveedoresRepositorio.save(proveedor);

        ProveedoresDTO resultDto = new ProveedoresDTO();
        resultDto.setId(updatedProveedor.getId());
        resultDto.setNombre(updatedProveedor.getNombre());
        resultDto.setValidado(updatedProveedor.getValidado());
        resultDto.setCif(updatedProveedor.getCif());
        resultDto.setNumVoluntarios(updatedProveedor.getNumVoluntarios());
        resultDto.setSede(updatedProveedor.getSede());
        resultDto.setUbicacion(updatedProveedor.getUbicacion());
        resultDto.setImg(updatedProveedor.getImg());
        resultDto.setUsername(updatedProveedor.getUsuario().getUsername());
        resultDto.setPassword(updatedProveedor.getUsuario().getPassword());
        resultDto.setEmail(updatedProveedor.getUsuario().getEmail());

        return resultDto;
    }

    public void eliminarProveedor(Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nombre = authentication.getName();
        Usuario usuario = usuarioService.buscarUsuarioPorNombre(nombre);

        if (usuario == null || !usuario.getRol().equals(Rol.ADMIN)) {
            throw new SecurityException("No tienes permiso para eliminar proveedores");
        }

        Proveedores proveedor = proveedoresRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));

        proveedoresRepositorio.delete(proveedor);
    }




    /**
     * crear proveedores siendo administrador
     */



    /**
     *
     */


}
