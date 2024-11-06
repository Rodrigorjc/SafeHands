package com.valencia.proyecto1evaluacion.controladores;

import com.valencia.proyecto1evaluacion.dtos.ProductoDTO;
import com.valencia.proyecto1evaluacion.servicios.ProductoService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
