package com.valencia.proyecto1evaluacion.dtos;

import com.valencia.proyecto1evaluacion.enums.Rol;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioCrearDTO {
    private String username;
    private String password;
    private String email;
    private Rol rol;
}
