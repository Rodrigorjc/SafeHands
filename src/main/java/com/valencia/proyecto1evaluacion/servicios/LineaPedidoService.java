package com.valencia.proyecto1evaluacion.servicios;


import com.valencia.proyecto1evaluacion.dtos.TotalDonacionesDTO;
import com.valencia.proyecto1evaluacion.repositorio.LineaPedidoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LineaPedidoService {

    private final LineaPedidoRepository lineaPedidoRepository;

    public TotalDonacionesDTO totalDonaciones() {
        TotalDonacionesDTO totalDonacionesDTO = new TotalDonacionesDTO();
        totalDonacionesDTO.setTotal(lineaPedidoRepository.obtenerTotalDonaciones());
        return totalDonacionesDTO;
    }
}
