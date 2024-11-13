package com.valencia.proyecto1evaluacion.controladores;

import com.valencia.proyecto1evaluacion.servicios.OngService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ong")
@RequiredArgsConstructor
public class OngController {

    private final OngService ongService;

    @PostMapping("/validar-proveedor/{id}")
    public void validarProveedor(@PathVariable Integer id) {
        ongService.validarSolicitudProveedor(id);
    }

    @DeleteMapping("/eliminar-proveedor/{id}")
    public void eliminarProveedor(@PathVariable Integer id) {
        ongService.eliminarSolicitudProveedor(id);
    }

}
