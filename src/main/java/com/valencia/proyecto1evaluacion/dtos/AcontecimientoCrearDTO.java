package com.valencia.proyecto1evaluacion.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AcontecimientoCrearDTO {
    private String nombre;
    private String descripcion;
    private String ubicacion;
}
