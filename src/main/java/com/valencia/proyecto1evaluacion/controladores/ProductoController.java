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


    @GetMapping("/listar")
    public List<ProductoDTO> mostrar(){
        return productoService.getAll();
    }

    //cambiar para buscar por ususario
    @GetMapping("/listar/{proveedorId}")
    public List<ProductoDTO> listarProductosPorProveedor(@PathVariable Integer proveedorId) {
        return productoService.getProductosByProveedorId(proveedorId);
    }

    @PostMapping("/crear")
    public ProductoDTO crearProducto(@RequestBody ProductoDTO productoDTO){
        return productoService.anyadirProducto(productoDTO);
    }

    @PutMapping("/editar")
    public Producto editarProducto( @RequestBody ProductoDTO productoDTO) {
        return productoService.editarProducto(productoDTO);
    }



    @DeleteMapping("/eliminar/{productoId}")
    public void eliminarProducto(@PathVariable Integer productoId) {
        productoService.eliminarProducto(productoId);
    }


    // Endpoint con filtros para buscar productos por precio, proveedor y nombre
//    @GetMapping("/buscar")
//    public ResponseEntity<List<Producto>> buscarProductos(
//            @RequestParam(value = "precioMin", required = false) Double precioMin,
//            @RequestParam(value = "precioMax", required = false) Double precioMax,
//            @RequestParam(value = "proveedor", required = false) Integer proveedor,
//            @RequestParam(value = "nombre", required = false) String nombre) {
//
//        // Llamar al servicio para obtener los productos filtrados
//        List<Producto> productos = productoService.buscarProductos(precioMin, precioMax, proveedor, nombre);
//
//        return ResponseEntity.ok(productos);
//    }




    @PostMapping("/{productoId}/vincular/{acontecimientoId}")
    public AconteciminetoProveedorVinculacionDTO vincularProductoAcontecimiento(@PathVariable Integer productoId, @PathVariable Integer acontecimientoId) {
        return productoService.vincularProductoAcontecimiento(productoId, acontecimientoId);
    }


//    @GetMapping("/acontecimiento")
//    public List<ProductoDTO> obtenerProductosPorAcontecimiento(@RequestParam Integer idAcontecimiento) {
//        return productoService.getProductosByAcontecimientoId(idAcontecimiento);
//    }


    @GetMapping("/acontecimiento/{idAcontecimiento}")
    public ResponseEntity<List<Producto>> obtenerProductosPorAcontecimiento(@PathVariable Integer idAcontecimiento) {
        List<Producto> productos = productoService.obtenerProductosPorAcontecimiento(idAcontecimiento);
        return ResponseEntity.ok(productos);
    }
    @GetMapping("/getProductosAcontecimiento/{id}")
    public List<ProductoDTO> getProductosAcontecimiento(@PathVariable Integer id) {
        return productoService.getProductosAcontecimiento(id);
    }

    @PutMapping("/editar/{productoId}")
    public Producto editarProducto(@PathVariable Integer productoId, @RequestBody ProductoDTO productoDto) {
        return productoService.editarProducto(productoId, productoDto);
    }



}
