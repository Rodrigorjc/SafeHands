package com.valencia.proyecto1evaluacion.controladores;

import com.valencia.proyecto1evaluacion.dtos.ProductoDTO;
import com.valencia.proyecto1evaluacion.servicios.ProveedoresAcontecimeintoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/proveedores-acontecimiento")
public class ProveedoresAcontecimientoController {
    @Autowired
    private ProveedoresAcontecimeintoService proveedorAcontecimientoService;

    @GetMapping("/productos")
    public List<ProductoDTO> obtenerProductosPorAcontecimiento(@RequestParam Integer idAcontecimiento) {
        return proveedorAcontecimientoService.obtenerProductosPorAcontecimiento(idAcontecimiento);
    }
}
