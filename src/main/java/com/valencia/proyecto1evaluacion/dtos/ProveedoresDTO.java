package com.valencia.proyecto1evaluacion.dtos;

import com.valencia.proyecto1evaluacion.enums.Rol;
import com.valencia.proyecto1evaluacion.modelos.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProveedoresDTO {

    private Integer id;
    private Integer numVoluntarios;

    private String sede;

    private String ubicacion;

    private String email;

    private String password;

    private String cif;

    private Rol rol= Rol.PROVEEDOR;

    private Integer id_usuario;

    private String username;

    private String token;

}
