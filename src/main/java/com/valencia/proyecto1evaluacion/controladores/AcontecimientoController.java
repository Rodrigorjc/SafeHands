package com.valencia.proyecto1evaluacion.controladores;

import com.valencia.proyecto1evaluacion.dtos.AcontecimientoDTO;
import com.valencia.proyecto1evaluacion.repositorio.AcontecimientoRepository;
import com.valencia.proyecto1evaluacion.servicios.AcontecimientoService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/acontecimiento")
@AllArgsConstructor
public class AcontecimientoController {
    private AcontecimientoRepository acontecimientoRepository;
    private AcontecimientoService acontecimientoService;

    @GetMapping("/listar")
    public List<AcontecimientoDTO> listarAcontecimientos(){
        List<AcontecimientoDTO> acontecimiento = acontecimientoService.getAll();
        return acontecimiento;
    }
}
