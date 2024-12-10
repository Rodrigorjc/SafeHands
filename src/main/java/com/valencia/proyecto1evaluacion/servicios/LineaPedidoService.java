package com.valencia.proyecto1evaluacion.servicios;


import com.valencia.proyecto1evaluacion.dtos.TotalDonacionesDTO;
import com.valencia.proyecto1evaluacion.dtos.ConsultasProveedorDTO;
import com.valencia.proyecto1evaluacion.dtos.ProveedoresDTO;
import com.valencia.proyecto1evaluacion.repositorio.LineaPedidoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio para gestionar las operaciones relacionadas con las líneas de pedido.
 * Este servicio proporciona métodos para obtener información sobre donaciones y recaudaciones
 * realizadas a través de las líneas de pedido.
 */
@Service
@AllArgsConstructor
public class LineaPedidoService {

    private final LineaPedidoRepository lineaPedidoRepository;

    /**
     * Obtiene el total de donaciones realizadas.
     * Este método consulta el repositorio de líneas de pedido para obtener la suma total de todas
     * las donaciones realizadas. La información se encapsula en un objeto TotalDonacionesDTO.
     *
     * @return Un objeto TotalDonacionesDTO que contiene el total de donaciones realizadas.
     *         El total se obtiene sumando todas las cantidades donadas registradas en las líneas de pedido.
     */
    public TotalDonacionesDTO totalDonaciones() {
        // Crea una nueva instancia de TotalDonacionesDTO
        TotalDonacionesDTO totalDonacionesDTO = new TotalDonacionesDTO();

        // Establece el total de donaciones obtenidas desde el repositorio
        totalDonacionesDTO.setTotal(lineaPedidoRepository.obtenerTotalDonaciones());
        // Devuelve el objeto TotalDonacionesDTO con el total de donaciones
        return totalDonacionesDTO;
    }


    /**
     * Obtiene el total recaudado por cada proveedor.
     * Este método consulta el repositorio de líneas de pedido para obtener el total recaudado por cada proveedor.
     * Los resultados se mapean a objetos ConsultasProveedorDTO, que contienen el nombre del proveedor y el total recaudado.
     *
     * @return Una lista de objetos ConsultasProveedorDTO que representan el total recaudado por cada proveedor.
     *         Cada objeto en la lista contiene el nombre del proveedor y el total recaudado por dicho proveedor.
     */
    public List<ConsultasProveedorDTO> findTotalRecaudadoPorProveedor() {
        // Obtiene los resultados crudos desde el repositorio
        List<Object[]> rawResults = lineaPedidoRepository.findTotalRecaudadoPorProveedorRaw();

        // Mapea los resultados crudos a objetos ConsultasProveedorDTO y los devuelve como una lista
        return rawResults.stream()
                .map(result -> ConsultasProveedorDTO.builder()
                        .nombre((String) result[0])  // Mapeo del campo "nombre"
                        .totalRecaudado(((Number) result[1]).floatValue())  // Mapeo de "total_recaudado"
                        .build())
                .collect(Collectors.toList());

    }

}
