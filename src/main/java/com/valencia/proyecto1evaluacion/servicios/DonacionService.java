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

    /**
     * Devuelve todas las donaciones.
     * Este método devuelve una lista de todas las donaciones simuladas.
     * Las donaciones simuladas se almacenan en una lista en memoria.
     *
     * @return una lista de objetos `DonacionDTO` que representan todas las donaciones realizadas
     */
    public List<DonacionDTO> obtenerDonaciones() {
        return donacionesSimuladas;
    }

    /**
     * Devuelve todas las donaciones de un usuario.
     * Este método devuelve una lista de todas las donaciones realizadas por un usuario específico.
     * Las donaciones se filtran por el ID del usuario.
     *
     * @return una lista de objetos `DonacionDTO` que representan todas las donaciones realizadas por el usuario
     */

    // Método para registrar una nueva donación
    public boolean registrarDonacion(DonacionDTO donacionDTO) {
        if (donacionDTO.getIdProducto() == null || donacionDTO.getCantidad() == null || donacionDTO.getCantidad() <= 0) {
            return false; // Validación simple
        }
        donacionesSimuladas.add(donacionDTO);
        return true;
    }
}