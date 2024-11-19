package com.valencia.proyecto1evaluacion.servicios;

import com.valencia.proyecto1evaluacion.dtos.AuthenticationDTO;
import com.valencia.proyecto1evaluacion.dtos.CrearProveedorDTO;
import com.valencia.proyecto1evaluacion.dtos.ProveedoresDTO;
import com.valencia.proyecto1evaluacion.enums.Rol;
import com.valencia.proyecto1evaluacion.modelos.Proveedores;
import com.valencia.proyecto1evaluacion.modelos.Usuario;
import com.valencia.proyecto1evaluacion.repositorio.ProveedoresRepository;
import com.valencia.proyecto1evaluacion.repositorio.UsuarioRepository;
import com.valencia.proyecto1evaluacion.security.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
        var jwtToken = jwtService.generateToken(usuario);

        return AuthenticationDTO.builder().token(jwtToken).build();
    }
}


