package com.valencia.proyecto1evaluacion.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AcontecimientoInfoDTO {
    private Integer id;
    private String nombre;
    private String img;
    private Double total;
}
