package com.valencia.proyecto1evaluacion.controladores;

import com.valencia.proyecto1evaluacion.dtos.DonacionDTO;
import com.valencia.proyecto1evaluacion.modelos.Pedido;
import com.valencia.proyecto1evaluacion.servicios.DonacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/producto")
public class DonacionController {

    private final DonacionService donacionService;

    @Autowired
    public DonacionController(DonacionService donacionService) {
        this.donacionService = donacionService;
    }

    // Endpoint para obtener todas las donaciones
    @GetMapping("/donaciones/obtener")
    public ResponseEntity<List<DonacionDTO>> obtenerDonaciones() {
        List<DonacionDTO> donaciones = donacionService.obtenerDonaciones();
        return ResponseEntity.ok(donaciones);
    }

    // Endpoint para registrar una nueva donación
    @PostMapping("/donaciones/registrar")
    public ResponseEntity<DonacionDTO> registrarDonacion(@RequestBody DonacionDTO donacionDTO) {
        boolean resultado = donacionService.registrarDonacion(donacionDTO);

        if (resultado) {
            // Retornamos el objeto recibido como JSON
            return ResponseEntity.ok(donacionDTO);
        } else {
            // En caso de error, podemos devolver un código HTTP y un JSON vacío
            return ResponseEntity.badRequest().build();
        }
    }
}