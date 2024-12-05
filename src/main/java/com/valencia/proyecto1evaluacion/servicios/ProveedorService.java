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


    /**
     * Este método se encarga de extraer todos los perfiles de proveedores de la base de datos.
     * Obtiene una lista de entidades Proveedores y las convierte a objetos PerfilProveedoresDTO.
     *
     * @return Una lista de objetos PerfilProveedoresDTO que representan los perfiles de los proveedores.
     */
    public List<PerfilProveedoresDTO> getAll() {
        // Obtener todos los proveedores de la base de datos
        List<Proveedores> proveedor = proveedoresRepositorio.findAll();
        // Crear una lista para almacenar los DTOs de los perfiles de proveedores
        List<PerfilProveedoresDTO> DTOS = new ArrayList<>();

        // Convertir cada entidad Proveedores a PerfilProveedoresDTO
        for (Proveedores p : proveedor) {
            PerfilProveedoresDTO dto = new PerfilProveedoresDTO();
            dto.setNombre(p.getNombre());
            dto.setUrl(p.getImg());
            dto.setCif(p.getCif());
            dto.setSede(p.getSede());
            dto.setNumVoluntarios(p.getNumVoluntarios());
            dto.setUbicacion(p.getUbicacion());

            // Agregar el DTO a la lista
            DTOS.add(dto);
        }
        // Devolver la lista de DTOs
        return DTOS;
    }

    /**
     * Este método busca perfiles de proveedores por coincidencia en nombre, descripción o sede.
     * Utiliza el término de búsqueda proporcionado para encontrar coincidencias en la base de datos.
     * Los resultados se convierten a objetos PerfilProveedoresDTO.
     *
     * @param busqueda El término de búsqueda para encontrar coincidencias en los perfiles de proveedores.
     * @return Una lista de objetos PerfilProveedoresDTO que representan los perfiles de proveedores coincidentes.
     */
    public List<PerfilProveedoresDTO> buscar(String busqueda) {
        // Convertir las entidades Proveedores a DTOs utilizando el perfilMapper
        return perfilMapper.toDTO(proveedoresRepositorio.buscar(busqueda));
    }

    /**
     * Este método busca un proveedor a partir de su id.
     * Utiliza el repositorio de proveedores para encontrar la entidad Proveedores correspondiente al id proporcionado.
     * Si no se encuentra ningún proveedor con el id dado, devuelve null.
     *
     * @param id El id del proveedor que se desea buscar.
     * @return La entidad Proveedores correspondiente al id proporcionado, o null si no se encuentra.
     */
    public Proveedores getById(Integer id) {

        return proveedoresRepositorio.findById(id).orElse(null);
    }

    /**
     * Este método guarda un perfil de proveedor nuevo o modifica uno existente.
     * Recibe un objeto PerfilProveedorCrearDTO que contiene la información del perfil del proveedor a guardar.
     * La información del DTO se mapea a una entidad Proveedores y se guarda en el repositorio de proveedores.
     *
     * @param dto Un objeto PerfilProveedorCrearDTO que contiene la información del perfil del proveedor a guardar.
     * @return La entidad Proveedores que ha sido guardada o modificada.
     */
    public Proveedores guardar(PerfilProveedorCrearDTO dto) {
        Proveedores perfilGuardar = new Proveedores();

        perfilGuardar.setNombre(dto.getNombre());
        perfilGuardar.setImg(dto.getUrl());
        perfilGuardar.setNumVoluntarios(dto.getNumVoluntarios());
        perfilGuardar.setSede(dto.getSede());
        perfilGuardar.setCif(dto.getCif());
        perfilGuardar.setUbicacion(dto.getUbicacion());

        return proveedoresRepositorio.save(perfilGuardar);
    }

    /**
     * Este método elimina un perfil de proveedor a través de su id.
     * Utiliza el repositorio de proveedores para eliminar la entidad Proveedores correspondiente al id proporcionado.
     * Devuelve un mensaje indicando si la eliminación fue exitosa o no.
     *
     * @param id El id del proveedor que se desea eliminar.
     * @return Un mensaje indicando el resultado de la operación de eliminación.
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


    /**
     * Este método elimina un perfil de proveedor.
     * Utiliza el repositorio de proveedores para eliminar la entidad Proveedores proporcionada.
     *
     * @param proveedor La entidad Proveedores que se desea eliminar.
     */
    public void eliminar(Proveedores proveedor) {

        proveedoresRepositorio.delete(proveedor);
    }

    /**
     * Este método obtiene el id de un proveedor a partir del id de su usuario.
     * Utiliza el repositorio de proveedores para encontrar el id del proveedor correspondiente al id de usuario proporcionado.
     *
     * @param id El id del usuario cuyo proveedor se desea buscar.
     * @return El id del proveedor correspondiente al id de usuario proporcionado.
     */
    public Integer getIdProveedorPorUsuarioId(Integer id) {

        return proveedoresRepositorio.findIdByUsuarioId(id);
    }
}


