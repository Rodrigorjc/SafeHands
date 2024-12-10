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




    @PostMapping("/{productoId}/vincular/{acontecimientoId}")
    public AconteciminetoProveedorVinculacionDTO vincularProductoAcontecimiento(@PathVariable Integer productoId, @PathVariable Integer acontecimientoId) {
        return productoService.vincularProductoAcontecimiento(productoId, acontecimientoId);
    }



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
