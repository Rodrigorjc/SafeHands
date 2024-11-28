package com.valencia.proyecto1evaluacion.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoCrearDTO {
    private Integer id;
    private String nombre;
    private String descripcion;
    private Float precio;
    private String url;
    private String nombreProveedor;
    private String nombreAcontecimiento;

}
