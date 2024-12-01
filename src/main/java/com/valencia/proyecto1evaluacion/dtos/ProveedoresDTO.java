package com.valencia.proyecto1evaluacion.dtos;

import com.valencia.proyecto1evaluacion.enums.Rol;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProveedoresDTO {

    private Integer id;

    private Integer numVoluntarios;

    private String nombre;

    private String sede;

    private String ubicacion;

    private String email;

    private String password;

    private String cif;

    private Rol rol= Rol.PROVEEDOR;

    private Integer idUsuario;

    private String username;

    private String token;

    private Integer cantidad;
    private Double precioUnitario;

}
