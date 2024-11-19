package com.valencia.proyecto1evaluacion.controladores;

import com.valencia.proyecto1evaluacion.dtos.ImgDTO;
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

    @GetMapping("/img/{id}")
    public ImgDTO getItemById(@PathVariable("id") String id) {
        try {
            Integer intId = Integer.parseInt(id);
            return ongService.getImgbyId(intId);
        } catch (NumberFormatException e) {
            // Handle the error appropriately
            return ongService.getImgbyId(10);
        }
    }
}
