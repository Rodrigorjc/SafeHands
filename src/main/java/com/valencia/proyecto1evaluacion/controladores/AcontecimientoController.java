package com.valencia.proyecto1evaluacion.controladores;

import com.valencia.proyecto1evaluacion.modelos.Acontecimiento;
import com.valencia.proyecto1evaluacion.repositorio.AcontecimientoRepositorio;
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
    private AcontecimientoRepositorio acontecimientoRepositorio;
    private AcontecimientoService acontecimientoService;

    @GetMapping("/listar")
    public List<Acontecimiento> listarAcontecimientos(){
        List<Acontecimiento> acontecimiento = acontecimientoService.getAll();
        return acontecimiento;
    }
}
