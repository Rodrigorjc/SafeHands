package com.valencia.proyecto1evaluacion.controladores;

import com.valencia.proyecto1evaluacion.dtos.PagosDTO;
import com.valencia.proyecto1evaluacion.servicios.PagosService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/total")
@AllArgsConstructor
public class PagosController {

    private final PagosService pagosService;

    @GetMapping("/donaciones")
    public List<PagosDTO> totalDonaciones() {
        return pagosService.findTotalDonaciones();
    }

}
