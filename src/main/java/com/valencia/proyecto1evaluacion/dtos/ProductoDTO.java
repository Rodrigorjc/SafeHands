package com.valencia.proyecto1evaluacion.dtos;

import com.valencia.proyecto1evaluacion.modelos.Proveedores;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoDTO {
    private Integer id;
    private String nombre;
    private String descripcion;
    private Float precio;
    private String url;
    private Integer idProveedores;
    private String nombreProveedor;
}
