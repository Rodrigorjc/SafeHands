package com.valencia.proyecto1evaluacion.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OngDTO {
    private Integer id;
    private Integer numVoluntarios;
    private String sede;
    private String descripcion;
    private String ubicacion;
    private String img;
    private String email;
    private String username;
    private String password;
    private Integer idUsuario;
}
