package com.valencia.proyecto1evaluacion.servicios;

import com.valencia.proyecto1evaluacion.dtos.ProductoDTO;
import com.valencia.proyecto1evaluacion.modelos.Producto;
import com.valencia.proyecto1evaluacion.repositorio.ProductoRepository;

import java.util.ArrayList;
import java.util.List;

public class ProductoService {
    ProveedorService proveedoresService;
    ProductoRepository productoRepositorio;


    public Producto anyadirProducto(ProductoDTO productoDto) {
        Producto producto = new Producto();
        producto.setDescripcion(productoDto.getDescripcion());
        producto.setUrl(productoDto.getUrl());
        producto.setProveedores(productoDto.getProveedor());
        producto.setPrecio(productoDto.getPrecio());
        producto.setNombre(productoDto.getNombre());
        return productoRepositorio.save(producto);
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
//            ProveedoresDto proveedoresDto = new ProveedoresDto();
//            proveedoresDto.setCIF(producto.getProveedores().getCIF());
//            proveedoresDto.setSede(producto.getProveedores().getSede());
//            proveedoresDto.setUbicacion(producto.getProveedores().getUbicacion());


            productoDtos.add(dto);
        }
        return productoDtos;


    }
}