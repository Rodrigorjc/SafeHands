package com.valencia.proyecto1evaluacion.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AcontecimientoOngVincularDTO {
    private Integer idAcontecimiento;
    private Integer idOng;
    private Integer idUsuario;
}
