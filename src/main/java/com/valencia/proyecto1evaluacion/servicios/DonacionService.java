package com.valencia.proyecto1evaluacion.servicios;

import com.valencia.proyecto1evaluacion.dtos.DonacionDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class DonacionService {

    private final List<DonacionDTO> donacionesSimuladas = new ArrayList<>();

    // Método para obtener todas las donaciones realizadas
    public List<DonacionDTO> obtenerDonaciones() {
        return donacionesSimuladas;
    }

    // Método para registrar una nueva donación
    public boolean registrarDonacion(DonacionDTO donacionDTO) {
        if (donacionDTO.getIdProducto() == null || donacionDTO.getCantidad() == null || donacionDTO.getCantidad() <= 0) {
            return false; // Validación simple
        }
        donacionesSimuladas.add(donacionDTO);
        return true;
    }
}