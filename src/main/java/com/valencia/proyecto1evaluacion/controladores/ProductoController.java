package com.valencia.proyecto1evaluacion.controladores;

import com.valencia.proyecto1evaluacion.dtos.ProductoDTO;
import com.valencia.proyecto1evaluacion.modelos.Producto;
import com.valencia.proyecto1evaluacion.servicios.ProductoService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/producto")
@AllArgsConstructor
public class ProductoController {

    ProductoService productoService;

//    @PostMapping("/crear")
//    public Producto crear(@RequestBody ProductoDto productoDto){
//        return productoService.anyadirProducto(productoDto);
//    }

    @GetMapping
    public List<ProductoDTO> mostrar(){
        return productoService.getAll();
    }

    @PostMapping("/crear")
    public Producto crearProducto(@RequestBody ProductoDTO productoDTO){
        return productoService.anyadirProducto(productoDTO);
    }

}
