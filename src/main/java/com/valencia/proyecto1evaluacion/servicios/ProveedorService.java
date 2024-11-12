package com.valencia.proyecto1evaluacion.servicios;

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


        public Proveedores registrarProveedor(ProveedoresDTO proveedorDto) {


            if (proveedorDto.getCif() == null || proveedorDto.getCif().isEmpty()) {
                throw new IllegalArgumentException("CIF cannot be null or empty");
            }
            if (proveedorDto.getNumVoluntarios() <= 0) {
                throw new IllegalArgumentException("Number of volunteers must be greater than zero");
            }

            Usuario usuario = new Usuario();
            usuario.setEmail(proveedorDto.getEmail());
            usuario.setUsername(proveedorDto.getUsername());
            usuario.setPassword(passwordEncoder.encode(proveedorDto.getPassword())); // Ensure to encode the password
            usuario.setRol(Rol.PROVEEDOR);
            usuario = usuarioRepositorio.save(usuario);


            String token = jwtService.generateToken(usuario);

            proveedorDto.setId_usuario(usuario.getId());

            return crearProveedor(proveedorDto);


        }
    }


