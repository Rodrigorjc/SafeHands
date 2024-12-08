package com.valencia.proyecto1evaluacion.servicios;

import com.valencia.proyecto1evaluacion.dtos.ImgDTO;
import com.valencia.proyecto1evaluacion.dtos.OngDTO;
import com.valencia.proyecto1evaluacion.enums.Rol;
import com.valencia.proyecto1evaluacion.modelos.Ong;
import com.valencia.proyecto1evaluacion.modelos.Proveedores;
import com.valencia.proyecto1evaluacion.modelos.Usuario;
import com.valencia.proyecto1evaluacion.repositorio.OngRepository;
import com.valencia.proyecto1evaluacion.repositorio.ProveedoresRepository;
import com.valencia.proyecto1evaluacion.repositorio.*;
import com.valencia.proyecto1evaluacion.security.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    /**
     * Metodo para validar una solicitud de proveedor siendo ONG
     * @param proveedorId
     */

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

    /**
     * Metodo para eliminar una solicitud de proveedor siendo ONG
     * @param proveedorId
     */
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

        Usuario usuarioProveedor = proveedor.getUsuario();
        if (usuarioProveedor != null) {
            usuarioRepositorio.delete(usuarioProveedor);
        }

        proveedoresRepositorio.delete(proveedor);
    }


    public OngDTO crearOng(OngDTO ongDto) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String nombre = authentication.getName();
//        Usuario usuario = usuarioService.buscarUsuarioPorNombre(nombre);
//
//        if (usuario == null || !usuario.getRol().equals(Rol.ADMIN)) {
//            throw new SecurityException("No tienes permiso para crear ONGs");
//        }

        Ong entity = new Ong();
        entity.setId(ongDto.getId());
        entity.setNumVoluntarios(ongDto.getNumVoluntarios());
        entity.setSede(ongDto.getSede());
        entity.setDescripcion(ongDto.getDescripcion());
        entity.setUbicacion(ongDto.getUbicacion());
        entity.setImg(ongDto.getImg());
        Usuario usuarioOng = usuarioRepositorio.findById(ongDto.getIdUsuario()).orElse(null);
        entity.setUsuario(usuarioOng);

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
     * Metodo para registrar una ONG siendo ADMIN
     * @param ongDto
     * @return OngDTO
     */

    public OngDTO registrarOng(OngDTO ongDto) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String nombre = authentication.getName();
//        Usuario usuario = usuarioService.buscarUsuarioPorNombre(nombre);
//
//        if (usuario == null || !usuario.getRol().equals(Rol.ADMIN)) {
//            throw new SecurityException("No tienes permiso para registrar ONGs");
//        }
//
//        if (ongDto.getNumVoluntarios() <= 0) {
//            throw new IllegalArgumentException("El numero de voluntarios no puede ser menor o igual a 0");
//        }

        Usuario usuarioOng = new Usuario();
        usuarioOng.setId(ongDto.getIdUsuario());
        usuarioOng.setEmail(ongDto.getEmail());
        usuarioOng.setUsername(ongDto.getUsername());
        usuarioOng.setPassword(passwordEncoder.encode(ongDto.getPassword()));
        usuarioOng.setRol(Rol.ONG);
        usuarioOng = usuarioRepositorio.save(usuarioOng);

        String token = jwtService.generateToken(usuarioOng, usuarioOng.getId(), usuarioOng.getRol().name());

        ongDto.setIdUsuario(usuarioOng.getId());

        return crearOng(ongDto);
    }

    /**
     * Metodo para obtener una ONG por su id
     * @param id
     * @return OngDTO
     */
    public OngDTO obtenerOngPorId(Integer id) {
        Ong ong = ongRepositorio.findOngByUsuarioId(id)
                .orElseThrow(() -> new RuntimeException("ONG no encontrada"));

        OngDTO dto = new OngDTO();
        dto.setId(ong.getId());
        dto.setNumVoluntarios(ong.getNumVoluntarios());
        dto.setSede(ong.getSede());
        dto.setDescripcion(ong.getDescripcion());
        dto.setUbicacion(ong.getUbicacion());
        dto.setImg(ong.getImg());
        dto.setEmail(ong.getUsuario().getEmail());
        dto.setUsername(ong.getUsuario().getUsername());
        dto.setIdUsuario(ong.getUsuario().getId());

        return dto;
    }


    public ImgDTO getImgbyId  (Integer id){
        Optional<Ong> optionalOng = ongRepositorio.findByUsuarioId(id);
        if (optionalOng.isPresent()) {
            Ong ong = optionalOng.get();
            ImgDTO imgDTO = new ImgDTO();
            imgDTO.setImg(ong.getImg());
            return imgDTO;
        } else {
            throw new RuntimeException("ONG not found");
        }
    }

    /**
     * Metodo para listar todas las ONGs
     * @return List<OngDTO>
     */
    public List<OngDTO> listar(){
      List<Ong> ongs = ongRepositorio.findAll();
      List<OngDTO> ongDTOs = new ArrayList<>();
        for (Ong ong : ongs) {
            OngDTO dto = new OngDTO();
            dto.setId(ong.getId());
            dto.setNumVoluntarios(ong.getNumVoluntarios());
            dto.setSede(ong.getSede());
            dto.setDescripcion(ong.getDescripcion());
            dto.setUbicacion(ong.getUbicacion());
            dto.setImg(ong.getImg());
            dto.setEmail(ong.getUsuario().getEmail());
            dto.setUsername(ong.getUsuario().getUsername());
            dto.setIdUsuario(ong.getUsuario().getId());
            ongDTOs.add(dto);
        }
        return ongDTOs;
    }

    /**
     * metodo para obtener el id de una ONG por el id de un usuario
     * @param usuarioId
     * @return Integer
     */

    public Integer obtenerOngIdPorUsuarioId(Integer usuarioId) {
        Ong ong = ongRepositorio.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new RuntimeException("Ong no encontrada para el usuario dado"));
        return ong.getId();
    }

}
