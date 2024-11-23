package com.valencia.proyecto1evaluacion.controladores;


import com.valencia.proyecto1evaluacion.dtos.TotalDonacionesDTO;
import com.valencia.proyecto1evaluacion.modelos.Ong;
import com.valencia.proyecto1evaluacion.repositorio.LineaPedidoRepository;
import com.valencia.proyecto1evaluacion.servicios.LineaPedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/linea")
@RequiredArgsConstructor
public class LineaPedidoController {

    private final LineaPedidoService lineaPedidoService;

    @GetMapping("/total")
    public TotalDonacionesDTO obtenerTotal() {
        return lineaPedidoService.totalDonaciones();
    }

}
