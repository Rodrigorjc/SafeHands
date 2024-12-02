package com.valencia.proyecto1evaluacion.servicios;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.valencia.proyecto1evaluacion.dtos.AconteciminetoProveedorVinculacionDTO;
import com.valencia.proyecto1evaluacion.dtos.ProductoDTO;
import com.valencia.proyecto1evaluacion.enums.Rol;
import com.valencia.proyecto1evaluacion.modelos.*;
import com.valencia.proyecto1evaluacion.repositorio.AcontecimientoRepository;
import com.valencia.proyecto1evaluacion.repositorio.ProductoRepository;
import com.valencia.proyecto1evaluacion.repositorio.ProveedoresAcontecimientoRepository;
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
    ProductoRepository productoRepositorio;
    @Autowired
    ProveedoresRepository proveedoresRepositorio;
    @Autowired
    UsuarioService usuarioService;
    @Autowired
    AcontecimientoRepository acontecimientoRepository;
    @Autowired
    ProveedoresAcontecimientoRepository proveedoresAcontecimientoRepository;


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
        if (productoDto.getPrecio() < 0) {
            throw new IllegalArgumentException("El precio no puede ser menor que 0");
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


        // Para comprobar que se ha creado correctamente el producto(OPCIONAL)
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(savedProducto);
            System.out.println("Producto en JSON: " + json);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return savedProducto;



    }

    public void eliminarProducto(Integer productoId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nombre = authentication.getName();
        Usuario usuario = usuarioService.buscarUsuarioPorNombre(nombre);

        if (usuario == null || !usuario.getRol().equals(Rol.PROVEEDOR)) {
            throw new SecurityException("No tienes permiso para eliminar productos");
        }

        Proveedores proveedor = proveedoresRepositorio.findByUsuarioId(usuario.getId())
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));

        Producto producto = productoRepositorio.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if (!producto.getProveedores().getId().equals(proveedor.getId())) {
            throw new SecurityException("No tienes permiso para eliminar este producto");
        }

        productoRepositorio.delete(producto);
    }


    public List<ProductoDTO> getAll() {
        List<ProductoDTO> productoDtos = new ArrayList<>();
        List<Producto> productos = productoRepositorio.findAll();
        for (Producto producto : productos) {
            ProductoDTO dto = new ProductoDTO();
            dto.setId(producto.getId());
            dto.setDescripcion(producto.getDescripcion());
            dto.setUrl(producto.getUrl());
            dto.setPrecio(producto.getPrecio());
            dto.setNombre(producto.getNombre());
            productoDtos.add(dto);
        }
        return productoDtos;
    }


    // Método que recibe los parámetros de filtro y llama al repositorio
//    public List<Producto> buscarProductos(Double precioMin, Double precioMax, Integer proveedor, String nombre) {
//        return productoRepositorio.buscarProductos(precioMin, precioMax, proveedor, nombre);
//    }





    public List<ProductoDTO> getProductosByProveedorId(Integer proveedorId) {
        List<Producto> productos = productoRepositorio.findByProveedoresUsuarioId(proveedorId);
        List<ProductoDTO> productoDtos = new ArrayList<>();
        for (Producto producto : productos) {
            ProductoDTO dto = new ProductoDTO();
            dto.setId(producto.getId());
            dto.setDescripcion(producto.getDescripcion());
            dto.setUrl(producto.getUrl());
            dto.setPrecio(producto.getPrecio());
            dto.setNombre(producto.getNombre());
            productoDtos.add(dto);
        }
        return productoDtos;
    }



    public AconteciminetoProveedorVinculacionDTO vincularProductoAcontecimiento(Integer productoId, Integer acontecimientoId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nombre = authentication.getName();
        Usuario usuario = usuarioService.buscarUsuarioPorNombre(nombre);

        if (usuario == null || !usuario.getRol().equals(Rol.PROVEEDOR)) {
            throw new SecurityException("No tienes permiso para vincular productos a acontecimientos");
        }

        Proveedores proveedor = proveedoresRepositorio.findByUsuarioId(usuario.getId())
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));

        Producto producto = productoRepositorio.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if (!producto.getProveedores().getId().equals(proveedor.getId())) {
            throw new SecurityException("No tienes permiso para vincular este producto a acontecimientos");
        }

        Acontecimiento acontecimiento = acontecimientoRepository.findById(acontecimientoId)
                .orElseThrow(() -> new RuntimeException("Acontecimiento no encontrado"));

        boolean isAlreadyAssociated = proveedoresAcontecimientoRepository.existsByProductoAndAcontecimiento(producto, acontecimiento);
        if (isAlreadyAssociated) {
            throw new RuntimeException("El producto ya está asociado a este acontecimiento");
        }

        ProveedoresAcontecimiento proveedoresAcontecimiento = new ProveedoresAcontecimiento();
        proveedoresAcontecimiento.setProducto(producto);
        proveedoresAcontecimiento.setAcontecimiento(acontecimiento);
        proveedoresAcontecimiento.setProveedores(proveedor);

        proveedoresAcontecimientoRepository.save(proveedoresAcontecimiento);

        AconteciminetoProveedorVinculacionDTO dto = new AconteciminetoProveedorVinculacionDTO();
        dto.setIdAcontecimiento(acontecimiento.getId());
        dto.setIdProveedor(proveedor.getId());
        dto.setIdProducto(producto.getId());

        return dto;
    }

//     Método para obtener los productos por el id del acontecimiento
//    public List<ProductoDTO> getProductosByAcontecimientoId(Integer idAcontecimiento) {
//        List<Producto> productos = productoRepositorio.findByAcontecimiento_Id(idAcontecimiento);
//
//        // Convertir la lista de Productos a DTOs si es necesario
//        return productos.stream()
//                .map(this::convertirAProductoDTO)
//                .collect(Collectors.toList());
//    }

    // Método para convertir un Producto a ProductoDTO (si lo necesitas)
    private ProductoDTO convertirAProductoDTO(Producto producto) {
        ProductoDTO productoDTO = new ProductoDTO();
        productoDTO.setId(producto.getId());
        productoDTO.setNombre(producto.getNombre());
        productoDTO.setUrl(producto.getUrl());
        productoDTO.setPrecio(producto.getPrecio());
        productoDTO.setDescripcion(producto.getDescripcion());
        // Puedes agregar otros atributos si es necesario
        return productoDTO;
    }

    public List<ProductoDTO> getProductosAcontecimiento(Integer id) {
        List<Producto> productos = proveedoresAcontecimientoRepository.findProductosByAcontecimientoId(id);
        List<ProductoDTO> productoDTOS = new ArrayList<>();
        for (Producto p: productos) {
            ProductoDTO productoDTO = new ProductoDTO();
            productoDTO.setDescripcion(p.getDescripcion());
            productoDTO.setUrl(p.getUrl());
            productoDTO.setPrecio(p.getPrecio());
            productoDTO.setNombre(p.getNombre());
            productoDTO.setId(p.getId());
            productoDTOS.add(productoDTO);
        }
        return productoDTOS;
    }

    public Producto getProductoById(Integer id) {
        try {
            return productoRepositorio.findById(id).orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener el producto", e);
        }
    }


    public Producto editarProducto(Integer productoId, ProductoDTO productoDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nombre = authentication.getName();
        Usuario usuario = usuarioService.buscarUsuarioPorNombre(nombre);

        if (usuario == null || !(usuario.getRol().equals(Rol.ADMIN) || usuario.getRol().equals(Rol.PROVEEDOR))) {
            throw new SecurityException("No tienes permiso para editar productos");
        }

        Producto producto = productoRepositorio.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if (usuario.getRol().equals(Rol.PROVEEDOR) && !producto.getProveedores().getUsuario().getId().equals(usuario.getId())) {
            throw new SecurityException("No tienes permiso para editar este producto");
        }

        producto.setDescripcion(productoDto.getDescripcion());
        producto.setUrl(productoDto.getUrl());
        producto.setPrecio(productoDto.getPrecio());
        producto.setNombre(productoDto.getNombre());

        return productoRepositorio.save(producto);
    }



}

