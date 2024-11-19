package com.valencia.proyecto1evaluacion.controladores;


import com.valencia.proyecto1evaluacion.dtos.ImgDTO;
import com.valencia.proyecto1evaluacion.servicios.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cliente")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @GetMapping("/img/{id}")
    public ImgDTO getItemById(@PathVariable("id") String id) {
        try {
            Integer intId = Integer.parseInt(id);
            return clienteService.getImgbyId(intId);
        } catch (NumberFormatException e) {
            // Handle the error appropriately
            return clienteService.getImgbyId(10);
        }
    }
}