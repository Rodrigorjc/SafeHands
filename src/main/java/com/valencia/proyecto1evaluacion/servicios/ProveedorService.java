package com.valencia.proyecto1evaluacion.servicios;

import com.valencia.proyecto1evaluacion.dtos.*;
import com.valencia.proyecto1evaluacion.enums.Rol;
import com.valencia.proyecto1evaluacion.mappers.PerfilMapper;
import com.valencia.proyecto1evaluacion.modelos.Proveedores;
import com.valencia.proyecto1evaluacion.modelos.Usuario;
import com.valencia.proyecto1evaluacion.repositorio.ProveedoresRepository;
import com.valencia.proyecto1evaluacion.repositorio.UsuarioRepository;
import com.valencia.proyecto1evaluacion.security.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    PasswordEncoder passwordEncoder;
    private PerfilMapper perfilMapper;

    JwtService jwtService;


    /**
     * crearProveedor con todos sus campos mediante la dto
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
        Usuario usuario = usuarioRepositorio.findById(proveedor.getId_usuario()).orElse(null);
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
        proveedor.setNumVoluntarios(crearProveedorDTO.getNumVoluntarios());
        proveedor.setCif(crearProveedorDTO.getCif());
        proveedor.setSede(crearProveedorDTO.getSede());
        proveedor.setUbicacion(crearProveedorDTO.getUbicacion());
        proveedoresRepositorio.save(proveedor);
        var jwtToken = jwtService.generateToken(usuario, usuario.getId(), usuario.getRol().name());

        return AuthenticationDTO.builder().token(jwtToken).build();
    }

    public ImgDTO getImgbyId(Integer id) {
        Proveedores proveedores = proveedoresRepositorio.findClienteByUsuarioId(id);
        ImgDTO imgDTO = new ImgDTO();
        imgDTO.setImg(proveedores.getImg());
        return imgDTO;
    }

    /**
     * listar proveedores
     */

    public List<ProveedoresDTO> listarProveedores() {
        List<Proveedores> proveedores = proveedoresRepositorio.findByValidadoFalse();
        List<ProveedoresDTO> proveedoresDTOs = new ArrayList<>();
        for (Proveedores proveedor : proveedores) {
            ProveedoresDTO dto = new ProveedoresDTO();
            dto.setId(proveedor.getId());
            dto.setCif(proveedor.getCif());
            dto.setNumVoluntarios(proveedor.getNumVoluntarios());
            dto.setSede(proveedor.getSede());
            dto.setUbicacion(proveedor.getUbicacion());
            dto.setId_usuario(proveedor.getUsuario().getId());
            dto.setEmail(proveedor.getUsuario().getEmail());
            dto.setUsername(proveedor.getUsuario().getUsername());
            proveedoresDTOs.add(dto);
        }
        return proveedoresDTOs;
    }


    /**
     * Este método extrae todos los perfiles de base de datos
     *
     * @return
     */
    public List<PerfilProveedoresDTO> getAll() {

        List<Proveedores> proveedor = proveedoresRepositorio.findAll();
        List<PerfilProveedoresDTO> DTOS = new ArrayList<>();

        for (Proveedores p : proveedor) {
            PerfilProveedoresDTO dto = new PerfilProveedoresDTO();
            dto.setNombre(p.getNombre());
            dto.setDescripcion(p.getDescripcion());
            dto.setUrl(p.getUrl());
            dto.setCif(p.getCif());
            dto.setSede(p.getSede());
            dto.setNumVoluntarios(p.getNumVoluntarios());
            dto.setUbicacion(p.getUbicacion());

            DTOS.add(dto);
        }

        return DTOS;
    }

    /**
     * Busca perfiles por coincidencia en nombre, descripcion o sede
     *
     * @param busqueda
     * @return
     */
    public List<PerfilProveedoresDTO> buscar(String busqueda) {
        return perfilMapper.toDTO(proveedoresRepositorio.buscar(busqueda));
    }

    /**
     * Este método busca un proveedor a partir de su id
     *
     * @param id
     * @return
     */
    public Proveedores getById(Integer id) {
        return proveedoresRepositorio.findById(id).orElse(null);
    }

    /**
     * Este método guarda un perfilProveedor nuevo o modifica uno existente
     *
     * @param dto
     * @return
     */
    public Proveedores guardar(PerfilProveedorCrearDTO dto) {
        Proveedores perfilGuardar = new Proveedores();
        perfilGuardar.setNombre(dto.getNombre());
        perfilGuardar.setDescripcion(dto.getDescripcion());
        perfilGuardar.setUrl(dto.getUrl());
        perfilGuardar.setNumVoluntarios(dto.getNumVoluntarios());
        perfilGuardar.setSede(dto.getSede());
        perfilGuardar.setCif(dto.getCif());
        perfilGuardar.setUbicacion(dto.getUbicacion());

        return proveedoresRepositorio.save(perfilGuardar);
    }

    /**
     * Elimina un perfilProveedor a traves de su id
     *
     * @param id
     */
    public String eliminar(Integer id) {
        String mensaje;
        Proveedores proveedores = getById(id);

        if (proveedores == null) {
            return "El perfil del proveedor con el id indicado no existe";
        }

        try {
            proveedoresRepositorio.deleteById(id);
            proveedores = getById(id);
            if (proveedores == null) {
                mensaje = "El perfil del proveedor no se ha podido eliminar.";
            } else {
                mensaje = "El perfil del proveedor se ha eliminado correctamente.";
            }
        } catch (Exception e) {
            mensaje = "El perfil del proveedor no se ha podido eliminar.";
        }
        return mensaje;
    }

    public void eliminar(Proveedores proveedor) {
        proveedoresRepositorio.delete(proveedor);
    }

}


