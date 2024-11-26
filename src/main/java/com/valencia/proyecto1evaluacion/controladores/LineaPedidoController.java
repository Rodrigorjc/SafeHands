package com.valencia.proyecto1evaluacion.controladores;

import com.valencia.proyecto1evaluacion.dtos.ConsultasProveedorDTO;
import com.valencia.proyecto1evaluacion.dtos.ProveedoresDTO;
import com.valencia.proyecto1evaluacion.servicios.LineaPedidoService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/peticiones")
@AllArgsConstructor
public class LineaPedidoController {

    private final LineaPedidoService lineaPedidoService;

    @GetMapping("/proveedores")
    public List<ConsultasProveedorDTO> llamadaLineaPedido() {
        return lineaPedidoService.findTotalRecaudadoPorProveedor();
    }


}
