package com.valencia.proyecto1evaluacion.servicios;

import com.valencia.proyecto1evaluacion.dtos.ProductoDTO;
import com.valencia.proyecto1evaluacion.modelos.ProveedoresAcontecimiento;
import com.valencia.proyecto1evaluacion.repositorio.ProveedoresAcontecimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProveedoresAcontecimeintoService {
    @Autowired
    private ProveedoresAcontecimientoRepository proveedoresAcontecimientoRepository;

    /**
     * Obtiene los productos vinculados a un acontecimiento.
     *
     * Este método recupera todas las vinculaciones de productos a un acontecimiento específico
     * utilizando el ID del acontecimiento proporcionado. Luego, convierte cada producto vinculado
     * en un objeto ProductoDTO para su uso en la capa de presentación.
     *
     * El proceso incluye:
     * 1. Recuperar todas las vinculaciones de ProveedoresAcontecimiento que coincidan con el ID del acontecimiento.
     * 2. Verificar si existen vinculaciones para evitar errores si la lista está vacía.
     * 3. Mapear cada entidad Producto a un objeto ProductoDTO.
     *
     * @param idAcontecimiento el ID del acontecimiento para el cual se desean obtener los productos vinculados.
     * @return una lista de objetos ProductoDTO que representan los productos vinculados al acontecimiento.
     *         Si no hay productos vinculados, se devuelve una lista vacía.
     */

    public List<ProductoDTO> obtenerProductosPorAcontecimiento(Integer idAcontecimiento) {
        // Obtener todas las vinculaciones de ProveedoresAcontecimiento por idAcontecimiento
        List<ProveedoresAcontecimiento> vinculaciones = proveedoresAcontecimientoRepository.findByAcontecimientoId(idAcontecimiento);

        // Comprobar si existen vinculaciones para evitar errores si la lista está vacía
        if (vinculaciones.isEmpty()) {
            return Collections.emptyList();
        }

        // Mapear las entidades Producto a ProductoDTO
        return vinculaciones.stream()
                .map(vinculacion -> vinculacion.getProducto()) // Obtener el producto de cada vinculación
                .map(producto -> new ProductoDTO(
                        producto.getId(),             // id del producto
                        producto.getNombre(),         // nombre del producto
                        producto.getDescripcion(),    // descripción del producto
                        producto.getPrecio(),         // precio del producto
                        producto.getUrl(),            // URL de imagen del producto
                        producto.getProveedores().getId() // id del proveedor del producto
                ))
                .collect(Collectors.toList());
    }
}