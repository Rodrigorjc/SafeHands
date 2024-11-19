package com.valencia.proyecto1evaluacion.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AconteciminetoProveedorVinculacionDTO {
    private Integer idAcontecimiento;
    private Integer idProveedor;
    private Integer idProducto;
}
