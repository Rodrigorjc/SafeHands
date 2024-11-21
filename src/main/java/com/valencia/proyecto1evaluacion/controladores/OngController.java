package com.valencia.proyecto1evaluacion.controladores;

import com.valencia.proyecto1evaluacion.dtos.AcontecimientoOngVincularDTO;
import com.valencia.proyecto1evaluacion.dtos.OngDTO;
import com.valencia.proyecto1evaluacion.modelos.Acontecimiento;
import com.valencia.proyecto1evaluacion.modelos.Ong;
import com.valencia.proyecto1evaluacion.servicios.OngService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ong")
@RequiredArgsConstructor
public class OngController {

    private final OngService ongService;

    @PostMapping("/validar/proveedor/{id}")
    public void validarProveedor(@PathVariable Integer id) {
        ongService.validarSolicitudProveedor(id);
    }

    @DeleteMapping("/eliminar/proveedor/{id}")
    public void eliminarProveedor(@PathVariable Integer id) {
        ongService.eliminarSolicitudProveedor(id);
    }


    @GetMapping("/detalles/{id}")
    public OngDTO   obtenerOngPorId(@PathVariable Integer id) {
        return ongService.obtenerOngPorId(id);
    }

    @PostMapping("/crear")
    public Ong crearOng(@RequestBody OngDTO ongDto) {
        return ongService.registrarOng(ongDto);
    }


    @PostMapping("/asociarAcontecimiento/{acontecimientoId}")
    public AcontecimientoOngVincularDTO asociarAcontecimiento(@PathVariable Integer acontecimientoId) {
       return ongService.acontecimientoOngVincular(acontecimientoId);

    }

//    @GetMapping("/acontecimiento/{id}")
//    public List<Ong> obtenerOngsPorAcontecimiento(@PathVariable Integer id) {
//        return ongService.obtenerOngsPorAcontecimiento(id);
//    }


//    @GetMapping("/{id}/acontecimientos")
//    public List<Acontecimiento> obtenerAcontecimientosPorOng(@PathVariable Integer id) {
//        return ongService.obtenerOngsPorAcontecimiento(id);
//    }

}
