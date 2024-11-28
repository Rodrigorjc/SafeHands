package com.valencia.proyecto1evaluacion.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PerfilProveedorCrearDTO {
    private String nombre;
    private String descripcion;
    private String url;
    private Integer numVoluntarios;
    private String sede;
    private String ubicacion;
    private String cif;
}
