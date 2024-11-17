package com.valencia.proyecto1evaluacion.servicios;

import com.valencia.proyecto1evaluacion.dtos.OngDTO;
import com.valencia.proyecto1evaluacion.enums.Rol;
import com.valencia.proyecto1evaluacion.modelos.Acontecimiento;
import com.valencia.proyecto1evaluacion.modelos.Ong;
import com.valencia.proyecto1evaluacion.modelos.Proveedores;
import com.valencia.proyecto1evaluacion.modelos.Usuario;
import com.valencia.proyecto1evaluacion.repositorio.AcontecimientoRepository;
import com.valencia.proyecto1evaluacion.repositorio.OngRepository;
import com.valencia.proyecto1evaluacion.repositorio.ProveedoresRepository;
import com.valencia.proyecto1evaluacion.repositorio.UsuarioRepository;
import com.valencia.proyecto1evaluacion.security.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OngService {
    @Autowired
    OngRepository ongRepositorio;
    @Autowired
    UsuarioService usuarioService;

    @Autowired
    ProveedoresRepository proveedoresRepositorio;

    @Autowired
    UsuarioRepository usuarioRepositorio;

    PasswordEncoder passwordEncoder;
    @Autowired
    JwtService jwtService;

    @Autowired
    AcontecimientoRepository acontecimientoRepository;

    public void validarSolicitudProveedor(Integer proveedorId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nombre = authentication.getName();
        Usuario usuario = usuarioService.buscarUsuarioPorNombre(nombre);

        if (usuario == null || !usuario.getRol().equals(Rol.ONG)) {
            throw new SecurityException("No tienes permiso para validar solicitudes de proveedores");
        }

        Proveedores proveedor = proveedoresRepositorio.findById(proveedorId)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));
        proveedor.setValidado(true);
        proveedoresRepositorio.save(proveedor);
    }

    public void eliminarSolicitudProveedor(Integer proveedorId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nombre = authentication.getName();
        Usuario usuario = usuarioService.buscarUsuarioPorNombre(nombre);

        if (usuario == null || !usuario.getRol().equals(Rol.ONG)) {
            throw new SecurityException("No tienes permiso para eliminar solicitudes de proveedores");
        }

        Proveedores proveedor = proveedoresRepositorio.findById(proveedorId)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));
        proveedoresRepositorio.delete(proveedor);
    }
    public Ong crearOng(OngDTO ongDto) {
        Ong entity = new Ong();
        entity.setNumVoluntarios(ongDto.getNumVoluntarios());
        entity.setSede(ongDto.getSede());
        entity.setDescripcion(ongDto.getDescripcion());
        entity.setUbicacion(ongDto.getUbicacion());
        entity.setImg(ongDto.getImg());
        Usuario usuario = usuarioRepositorio.findById(ongDto.getId_usuario()).orElse(null);
        entity.setUsuario(usuario);

        return ongRepositorio.save(entity);
    }

    public Ong registrarOng(OngDTO ongDto) {

        if (ongDto.getNumVoluntarios() <= 0) {
            throw new IllegalArgumentException("El numero de voluntarios no puede ser menor o igual a 0");
        }

        Usuario usuario = new Usuario();
        usuario.setEmail(ongDto.getEmail());
        usuario.setUsername(ongDto.getUsername());
        usuario.setPassword(passwordEncoder.encode(ongDto.getPassword()));
        usuario.setRol(Rol.ONG);
        usuario = usuarioRepositorio.save(usuario);

        String token = jwtService.generateToken(usuario);

        ongDto.setId_usuario(usuario.getId());

        return crearOng(ongDto);
    }

    public Ong obtenerOngPorId(Integer id) {
        return ongRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("ONG no encontrada"));
    }




}
