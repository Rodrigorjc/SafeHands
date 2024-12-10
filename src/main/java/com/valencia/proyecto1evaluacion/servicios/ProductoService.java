package com.valencia.proyecto1evaluacion.servicios;

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


    /**
     * Metodo para añadir un producto siendo proveedor y admin
     * @param productoDto
     * @return Producto
     */
    public ProductoDTO anyadirProducto(ProductoDTO productoDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nombre = authentication.getName();
        Usuario usuario = usuarioService.buscarUsuarioPorNombre(nombre);

        if (usuario == null || !(usuario.getRol().equals(Rol.PROVEEDOR) || usuario.getRol().equals(Rol.ADMIN))) {
            throw new SecurityException("No tienes permiso para añadir productos");
        }

        Proveedores proveedor;

        if (productoDto.getIdProveedores() == null) {
            proveedor = proveedoresRepositorio.findByUsuarioId(usuario.getId())
                    .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));
        } else {
            proveedor = proveedoresRepositorio.findById(productoDto.getIdProveedores())
                    .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));
        }

        if (!proveedoresRepositorio.existsByIdAndValidado(proveedor.getId(), true)) {
            throw new RuntimeException("El proveedor no está validado y no puede crear productos.");
        }
        if (productoDto.getPrecio() <= 0) {
            throw new IllegalArgumentException("El precio no puede ser menor que 0");
        }

        if (!proveedor.getUsuario().getId().equals(usuario.getId())) {
            throw new SecurityException("No tienes permiso para añadir productos para este proveedor");
        }

        Producto producto = new Producto();
        producto.setDescripcion(productoDto.getDescripcion());
        producto.setUrl(productoDto.getUrl());
        producto.setPrecio(productoDto.getPrecio());
        producto.setNombre(productoDto.getNombre());
        producto.setProveedores(proveedor);
        Producto savedProducto = productoRepositorio.save(producto);

        // Convert the saved Producto to ProductoDTO
        ProductoDTO savedProductoDto = new ProductoDTO();
        savedProductoDto.setId(savedProducto.getId());
        savedProductoDto.setDescripcion(savedProducto.getDescripcion());
        savedProductoDto.setUrl(savedProducto.getUrl());
        savedProductoDto.setPrecio(savedProducto.getPrecio());
        savedProductoDto.setNombre(savedProducto.getNombre());
        savedProductoDto.setIdProveedores(savedProducto.getProveedores().getId());

        return savedProductoDto;
    }

    /**
     * Edita un producto existente.
     *
     * Este método permite a un usuario con rol de PROVEEDOR o ADMIN editar un producto existente.
     * Se validan los permisos del usuario y se actualizan los campos del producto según los datos proporcionados.
     *
     * @param productoDto el objeto ProductoDTO que contiene los datos actualizados del producto
     * @return el producto actualizado
     * @throws SecurityException si el usuario no tiene permiso para editar productos
     * @throws RuntimeException si el producto o el proveedor no se encuentran
     */

    public Producto editarProducto(ProductoDTO productoDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nombre = authentication.getName();
        Usuario usuario = usuarioService.buscarUsuarioPorNombre(nombre);

        if (usuario == null || !(usuario.getRol().equals(Rol.PROVEEDOR) || usuario.getRol().equals(Rol.ADMIN))){
            throw new SecurityException("No tienes permiso para editar productos");
        }

        Producto producto = productoRepositorio.findById(productoDto
                        .getId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if(!productoDto.getNombre().isEmpty()){
            producto.setNombre(productoDto.getNombre());
        }
        if(!productoDto.getDescripcion().isEmpty()){
            producto.setDescripcion(productoDto.getDescripcion());
        }
        if(!productoDto.getUrl().isEmpty()){
            producto.setUrl(productoDto.getUrl());
        }
        if(productoDto.getPrecio() != null){
            producto.setPrecio(productoDto.getPrecio());
        }
        if(productoDto.getIdProveedores() != null){
            Proveedores proveedor = proveedoresRepositorio.findById(productoDto.getIdProveedores())
                    .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));
            producto.setProveedores(proveedor);
        }

        return productoRepositorio.save(producto);

    }

    /**
     * Metodo para eliminar un producto siendo proveedor
     * @param productoId
     */
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


    /**
     * Metodo para obtener todos los productos
     * @return List<ProductoDTO>
     */
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


    /**
     * Metodo para obtener los productos por el id del proveedor
     * @param proveedorId
     * @return List<ProductoDTO>
     */
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


    /**
     * Metodo para vincular un producto a un acontecimiento teniendo el rol de PROVEEDOR
     * @param productoId
     * @param acontecimientoId
     * @return AconteciminetoProveedorVinculacionDTO
     */
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



    /**
     * Método para convertir un Producto a ProductoDTO.
     *
     * Este método convierte un objeto Producto a un objeto ProductoDTO.
     *
     * @param producto el objeto Producto a convertir
     * @return el objeto ProductoDTO convertido
     */

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

    /**
     * Método para obtener los productos por el id del acontecimiento.
     *
     * Este método devuelve una lista de productos asociados a un acontecimiento específico.
     *
     * @param idAcontecimiento el id del acontecimiento
     * @return una lista de productos asociados al acontecimiento
     */


    public List<Producto> obtenerProductosPorAcontecimiento(Integer idAcontecimiento) {
        return productoRepositorio.findProductosByAcontecimiento(idAcontecimiento);
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


    /**
     * Metodo para editar un producto siendo proveedor y admin
     * @param productoId
     * @param productoDto
     * @return Producto
     */

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
