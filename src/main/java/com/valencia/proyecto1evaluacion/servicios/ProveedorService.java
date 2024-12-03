package com.valencia.proyecto1evaluacion.servicios;

import com.valencia.proyecto1evaluacion.dtos.*;
import com.valencia.proyecto1evaluacion.enums.Rol;
import com.valencia.proyecto1evaluacion.modelos.Proveedores;
import com.valencia.proyecto1evaluacion.modelos.Usuario;
import com.valencia.proyecto1evaluacion.repositorio.ProveedoresRepository;
import com.valencia.proyecto1evaluacion.repositorio.UsuarioRepository;
import com.valencia.proyecto1evaluacion.security.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ProveedorService {

    @Autowired
    ProveedoresRepository proveedoresRepositorio;

    @Autowired
    UsuarioRepository usuarioRepositorio;

    @Autowired
    UsuarioService usuarioService;

    PasswordEncoder passwordEncoder;

    JwtService jwtService;



    /**crearProveedor con todos sus campos mediante la dto
     *
     * @param proveedor
     * @return
     */
    public Proveedores crearProveedor(ProveedoresDTO proveedor) {
        Proveedores entity = new Proveedores();
        entity.setCif(proveedor.getCif());
        entity.setNumVoluntarios(proveedor.getNumVoluntarios());
        entity.setSede(proveedor.getSede());
        entity.setUbicacion(proveedor.getUbicacion());
        Usuario usuario = usuarioRepositorio.findById(proveedor.getIdUsuario()).orElse(null);
        entity.setUsuario(usuario);

        return proveedoresRepositorio.save(entity);
    }

    public AuthenticationDTO registrarProveedor(CrearProveedorDTO crearProveedorDTO) {
        Usuario usuario = new Usuario();
        usuario.setEmail(crearProveedorDTO.getEmail());
        usuario.setUsername(crearProveedorDTO.getUsername());
        usuario.setPassword(passwordEncoder.encode(crearProveedorDTO.getPassword()));
        usuario.setRol(Rol.PROVEEDOR);
        usuarioRepositorio.save(usuario);
        Proveedores proveedor = new Proveedores();
        proveedor.setUsuario(usuario);
        proveedor.setImg(crearProveedorDTO.getImg());
        proveedor.setNombre(crearProveedorDTO.getNombre());
        proveedor.setValidado(false);
        proveedor.setNumVoluntarios(crearProveedorDTO.getNumVoluntarios());
        proveedor.setCif(crearProveedorDTO.getCif());
        proveedor.setSede(crearProveedorDTO.getSede());
        proveedor.setUbicacion(crearProveedorDTO.getUbicacion());
        proveedoresRepositorio.save(proveedor);
        var jwtToken = jwtService.generateToken(usuario, usuario.getId(), usuario.getRol().name());

        return AuthenticationDTO.builder().token(jwtToken).build();
    }



    public AuthenticationDTO registrarProveedorAdmin(CrearProveedorDTO crearProveedorDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nombre = authentication.getName();
        Usuario usuarioNombre = usuarioService.buscarUsuarioPorNombre(nombre);

        if (usuarioNombre == null || !usuarioNombre.getRol().equals(Rol.ADMIN)) {
            throw new SecurityException("No tienes permiso para eliminar ONGs");
        }

        Usuario usuario = new Usuario();
        usuario.setEmail(crearProveedorDTO.getEmail());
        usuario.setUsername(crearProveedorDTO.getUsername());
        usuario.setPassword(passwordEncoder.encode(crearProveedorDTO.getPassword()));
        usuario.setRol(Rol.PROVEEDOR);
        usuarioRepositorio.save(usuario);
        Proveedores proveedor = new Proveedores();
        proveedor.setUsuario(usuario);
        proveedor.setImg(crearProveedorDTO.getImg());
        proveedor.setNombre(crearProveedorDTO.getNombre());
        proveedor.setValidado(true);
        proveedor.setNumVoluntarios(crearProveedorDTO.getNumVoluntarios());
        proveedor.setCif(crearProveedorDTO.getCif());
        proveedor.setSede(crearProveedorDTO.getSede());
        proveedor.setUbicacion(crearProveedorDTO.getUbicacion());
        proveedoresRepositorio.save(proveedor);
        var jwtToken = jwtService.generateToken(usuario, usuario.getId(), usuario.getRol().name());

        return AuthenticationDTO.builder().token(jwtToken).build();
    }

    public ImgDTO getImgbyId  (Integer id){
        Proveedores proveedores = proveedoresRepositorio.findClienteByUsuarioId(id);
        ImgDTO imgDTO = new ImgDTO();
        imgDTO.setImg(proveedores.getImg());
        return imgDTO;
    }

    /**
     * listar proveedores
     */

    public List<ProveedoresDTO> listarProveedoresPorValidadoFalso() {
        List<Proveedores> proveedores = proveedoresRepositorio.findByValidadoFalse();
        List<ProveedoresDTO> proveedoresDTOs = new ArrayList<>();
        for (Proveedores proveedor : proveedores) {
            ProveedoresDTO dto = new ProveedoresDTO();
            dto.setId(proveedor.getId());
            dto.setNombre(proveedor.getNombre());
            dto.setCif(proveedor.getCif());
            dto.setNumVoluntarios(proveedor.getNumVoluntarios());
            dto.setSede(proveedor.getSede());
            dto.setUbicacion(proveedor.getUbicacion());
            dto.setIdUsuario(proveedor.getUsuario().getId());
            dto.setEmail(proveedor.getUsuario().getEmail());
            dto.setUsername(proveedor.getUsuario().getUsername());
            proveedoresDTOs.add(dto);
        }
        return proveedoresDTOs;
    }

    public ProveedoresDTO obtenerProveedorPorId(Integer idProveedor) {
        Proveedores proveedor = proveedoresRepositorio.findByUsuarioId(idProveedor)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));
        ProveedoresDTO dto = new ProveedoresDTO();
        dto.setId(proveedor.getId());
        dto.setNombre(proveedor.getNombre());
        dto.setCif(proveedor.getCif());
        dto.setNumVoluntarios(proveedor.getNumVoluntarios());
        dto.setSede(proveedor.getSede());
        dto.setUbicacion(proveedor.getUbicacion());
        dto.setIdUsuario(proveedor.getUsuario().getId());
        dto.setEmail(proveedor.getUsuario().getEmail());
        dto.setUsername(proveedor.getUsuario().getUsername());
        return dto;
    }



    public List<ProveedoresSliderDTO> listadoProveedoresSlider() {
        List<Proveedores> proveedores = proveedoresRepositorio.findAll();
        List<ProveedoresSliderDTO> proveedoresSliderDTOS = new ArrayList<>();
        for (Proveedores p: proveedores) {
            ProveedoresSliderDTO proveedoresSliderDTO = new ProveedoresSliderDTO();
            proveedoresSliderDTO.setNombre(p.getNombre());
            proveedoresSliderDTO.setImg(p.getImg());
            proveedoresSliderDTO.setId(p.getId());
            proveedoresSliderDTOS.add(proveedoresSliderDTO);
        }
        return proveedoresSliderDTOS;
    }

    public List<ProveedorRankingDTO> obtenerRankingProveedores() {
        return proveedoresRepositorio.obtenerRankingProveedores();
    }

    public List<ProveedorInfoDTO> obtenerInfoProveedores() {
        return proveedoresRepositorio.obtenerInfoProveedores();
    }

    public Integer obtenerProveedorIdPorUsuarioId(Integer usuarioId) {
        Proveedores proveedor = proveedoresRepositorio.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado para el usuario dado"));
        return proveedor.getId();
    }



}



