package com.valencia.proyecto1evaluacion.servicios;

import com.valencia.proyecto1evaluacion.dtos.ConsultasProveedorDTO;
import com.valencia.proyecto1evaluacion.dtos.ProveedoresDTO;
import com.valencia.proyecto1evaluacion.repositorio.LineaPedidoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LineaPedidoService {

    private final LineaPedidoRepository lineaPedidoRepository;

    public List<ConsultasProveedorDTO> findTotalRecaudadoPorProveedor() {
        List<Object[]> rawResults = lineaPedidoRepository.findTotalRecaudadoPorProveedorRaw();

        return rawResults.stream()
                .map(result -> ConsultasProveedorDTO.builder()
                        .nombre((String) result[0])  // Mapeo del campo "nombre"
                        .totalRecaudado(((Number) result[1]).floatValue())  // Mapeo de "total_recaudado"
                        .build())
                .collect(Collectors.toList());

    }
}
