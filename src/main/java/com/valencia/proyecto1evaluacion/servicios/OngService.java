package com.valencia.proyecto1evaluacion.servicios;

import com.valencia.proyecto1evaluacion.enums.Rol;
import com.valencia.proyecto1evaluacion.modelos.Proveedores;
import com.valencia.proyecto1evaluacion.modelos.Usuario;
import com.valencia.proyecto1evaluacion.repositorio.OngRepository;
import com.valencia.proyecto1evaluacion.repositorio.ProveedoresRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OngService {
    @Autowired
    OngRepository ongRepositorio;
    @Autowired
    UsuarioService usuarioService;

    @Autowired
    ProveedoresRepository proveedoresRepositorio;

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


}
