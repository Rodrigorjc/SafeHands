package com.valencia.proyecto1evaluacion.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AcontecimientoDTO {
    private Integer idAcontecimiento;
    private String nombre;
    private String descripcion;
    private String ubicacion;
    private String img;
}
