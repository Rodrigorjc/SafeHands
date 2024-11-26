package com.valencia.proyecto1evaluacion.dtos;

import lombok.Data;

@Data
public class ClienteDTO {
    private Integer id;
    private String dni;
    private Integer idUsuario;  // Relación con el Usuario a través de su ID
}