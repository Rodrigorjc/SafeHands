package com.valencia.proyecto1evaluacion.controladores;

import com.valencia.proyecto1evaluacion.dtos.PagosDTO;
import com.valencia.proyecto1evaluacion.dtos.PedidoDTO;
import com.valencia.proyecto1evaluacion.servicios.PagosService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/total")
@AllArgsConstructor
public class PagosController {

    private final PagosService pagosService;

    @GetMapping("/donaciones")
    public PagosDTO totalDonaciones() {
        return pagosService.findTotalDonaciones();
    }

    @PostMapping("/crear")
    public PagosDTO crearPagos(@RequestBody PagosDTO pagosDTO){
        return pagosService.crearPagos(pagosDTO);
    }

}
