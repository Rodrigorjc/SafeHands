package com.valencia.proyecto1evaluacion.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientePerfilDTO {
    private String dni;
    private String img;
    private String email;
    private String username;
}
