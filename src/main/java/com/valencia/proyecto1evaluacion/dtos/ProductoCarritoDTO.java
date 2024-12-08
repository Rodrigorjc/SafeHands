package com.valencia.proyecto1evaluacion.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductoCarritoDTO {

    private Integer idProducto;
    private Integer cantidad;
    private Double precioUnitario;
    private Double total;
    private Integer idAcontecimiento;

}
