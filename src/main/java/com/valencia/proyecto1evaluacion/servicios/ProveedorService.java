package com.valencia.proyecto1evaluacion.servicios;

import com.valencia.proyecto1evaluacion.dtos.AuthenticationDTO;
import com.valencia.proyecto1evaluacion.dtos.CrearProveedorDTO;
import com.valencia.proyecto1evaluacion.dtos.ImgDTO;
import com.valencia.proyecto1evaluacion.dtos.ProveedoresDTO;
import com.valencia.proyecto1evaluacion.enums.Rol;
import com.valencia.proyecto1evaluacion.modelos.Cliente;
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

    public ImgDTO getImgbyId  (Integer id){
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

    public Integer obtenerIdProveedor(Integer idProveedor) {
        return proveedoresRepositorio.findById(idProveedor)
                .map(Proveedores::getId)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));
    }

}


