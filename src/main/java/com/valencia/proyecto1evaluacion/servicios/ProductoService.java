package com.valencia.proyecto1evaluacion.servicios;

import com.valencia.proyecto1evaluacion.dtos.ProductoDTO;
import com.valencia.proyecto1evaluacion.modelos.Producto;
import com.valencia.proyecto1evaluacion.modelos.Proveedores;
import com.valencia.proyecto1evaluacion.repositorio.ProductoRepository;
import com.valencia.proyecto1evaluacion.repositorio.ProveedoresRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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


    public Producto anyadirProducto(ProductoDTO productoDto) {
        Producto producto = new Producto();
        producto.setDescripcion(productoDto.getDescripcion());
        producto.setUrl(productoDto.getUrl());
        producto.setPrecio(productoDto.getPrecio());
        producto.setNombre(productoDto.getNombre());

        Proveedores proveedor = proveedoresRepositorio.findById(productoDto.getIdProveedor()).orElse(null);
        producto.setProveedores(proveedor);

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
            productoDtos.add(dto);
        }
        return productoDtos;
    }
}