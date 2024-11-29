package com.valencia.proyecto1evaluacion.controladores;
import com.valencia.proyecto1evaluacion.dtos.TotalDonacionesDTO;
import com.valencia.proyecto1evaluacion.dtos.ConsultasProveedorDTO;
import com.valencia.proyecto1evaluacion.servicios.LineaPedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
//@RequestMapping("/linea")
@RequestMapping("/peticiones")
@RequiredArgsConstructor
public class LineaPedidoController {

    private final LineaPedidoService lineaPedidoService;

    @GetMapping("/total")
    public TotalDonacionesDTO obtenerTotal() {
        return lineaPedidoService.totalDonaciones();
    }

    @GetMapping("/proveedores")
    public List<ConsultasProveedorDTO> llamadaLineaPedido() {
        return lineaPedidoService.findTotalRecaudadoPorProveedor();
    }

}
