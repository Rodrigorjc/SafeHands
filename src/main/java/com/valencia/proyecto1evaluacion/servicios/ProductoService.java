package com.valencia.proyecto1evaluacion.servicios;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.valencia.proyecto1evaluacion.dtos.ProductoDTO;
import com.valencia.proyecto1evaluacion.enums.Rol;
import com.valencia.proyecto1evaluacion.modelos.Producto;
import com.valencia.proyecto1evaluacion.modelos.Proveedores;
import com.valencia.proyecto1evaluacion.modelos.Usuario;
import com.valencia.proyecto1evaluacion.repositorio.ProductoRepository;
import com.valencia.proyecto1evaluacion.repositorio.ProveedoresRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductoService {

    @Autowired
    ProveedorService proveedoresService;
    @Autowired
    ProductoRepository productoRepositorio;
    @Autowired
    ProveedoresRepository proveedoresRepositorio;
    @Autowired
    UsuarioService usuarioService;


    public Producto anyadirProducto(ProductoDTO productoDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nombre = authentication.getName();
        Usuario usuario = usuarioService.buscarUsuarioPorNombre(nombre);

        if (usuario == null || !usuario.getRol().equals(Rol.PROVEEDOR)) {
            throw new SecurityException("No tienes permiso para añadir productos");
        }


//        if (productoDto.getIdProveedor() == null) {
//            throw new IllegalArgumentException("El id del proveedor no debe ser nulo");
//        }
//
        Proveedores proveedor = proveedoresRepositorio.findByUsuarioId(usuario.getId())
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));

        if (!proveedoresRepositorio.existsByIdAndValidado(proveedor.getId(), true)) {
            throw new RuntimeException("El proveedor no está validado y no puede crear productos.");
        }

//        if (!proveedor.getUsuario().getId().equals(usuario.getId())) {
//            throw new SecurityException("No tienes permiso para añadir productos para este proveedor");
//        }

        Producto producto = new Producto();
        producto.setDescripcion(productoDto.getDescripcion());
        producto.setUrl(productoDto.getUrl());
        producto.setPrecio(productoDto.getPrecio());
        producto.setNombre(productoDto.getNombre());
        producto.setProveedores(proveedor);
        Producto savedProducto = productoRepositorio.save(producto);

        // Convert the saved product to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(savedProducto);
            System.out.println("Producto en JSON: " + json);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return savedProducto;



    }


    public List<ProductoDTO> getAll() {
        List<ProductoDTO> productoDtos = new ArrayList<>();
        List<Producto> productos = productoRepositorio.findAll();
        for (Producto producto : productos) {
            ProductoDTO dto = new ProductoDTO();
            dto.setDescripcion(producto.getDescripcion());
            dto.setUrl(producto.getUrl());
            dto.setPrecio(producto.getPrecio());
            dto.setNombre(producto.getNombre());
            productoDtos.add(dto);
        }
        return productoDtos;
    }
}