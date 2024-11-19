package com.valencia.proyecto1evaluacion.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CrearProveedorDTO {
    private Integer numVoluntarios;
    private String sede;
    private String ubicacion;
    private String cif;
    private String username;
    private String email;
    private String password;
}
