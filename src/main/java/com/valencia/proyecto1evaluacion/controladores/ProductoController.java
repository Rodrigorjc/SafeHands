package com.valencia.proyecto1evaluacion.controladores;

import com.valencia.proyecto1evaluacion.dtos.AconteciminetoProveedorVinculacionDTO;
import com.valencia.proyecto1evaluacion.dtos.ProductoDTO;
import com.valencia.proyecto1evaluacion.modelos.Producto;
import com.valencia.proyecto1evaluacion.servicios.ProductoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/producto")
@AllArgsConstructor
public class    ProductoController {

    ProductoService productoService;

//    @PostMapping("/crear")
//    public Producto crear(@RequestBody ProductoDto productoDto){
//        return productoService.anyadirProducto(productoDto);
//    }

    @GetMapping("/listar")
    public List<ProductoDTO> mostrar(){
        return productoService.getAll();
    }

    @GetMapping("/listar/{proveedorId}")
    public List<ProductoDTO> listarProductosPorProveedor(@PathVariable Integer proveedorId) {
        return productoService.getProductosByProveedorId(proveedorId);
    }

    @PostMapping("/crear")
//    @PreAuthorize("hasAuthority('PROVEEDOR')")
    public Producto crearProducto(@RequestBody ProductoDTO productoDTO){
        return productoService.anyadirProducto(productoDTO);
    }


    // Endpoint con filtros para buscar productos por precio, proveedor y nombre
    @GetMapping("/buscar")
    public ResponseEntity<List<Producto>> buscarProductos(
            @RequestParam(value = "precioMin", required = false) Double precioMin,
            @RequestParam(value = "precioMax", required = false) Double precioMax,
            @RequestParam(value = "proveedor", required = false) Integer proveedor,
            @RequestParam(value = "nombre", required = false) String nombre) {

        // Llamar al servicio para obtener los productos filtrados
        List<Producto> productos = productoService.buscarProductos(precioMin, precioMax, proveedor, nombre);

        return ResponseEntity.ok(productos);
    }




    @PostMapping("/{productoId}/vincular/{acontecimientoId}")
    public AconteciminetoProveedorVinculacionDTO vincularProductoAcontecimiento(@PathVariable Integer productoId, @PathVariable Integer acontecimientoId) {
        return productoService.vincularProductoAcontecimiento(productoId, acontecimientoId);
    }

}
