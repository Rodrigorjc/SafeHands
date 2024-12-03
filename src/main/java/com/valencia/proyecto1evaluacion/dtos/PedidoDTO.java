package com.valencia.proyecto1evaluacion.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDTO {
    private Integer idUsuario;
    private List<ProductoCarritoDTO> productos;
}
