package com.valencia.proyecto1evaluacion.dtos;

import com.valencia.proyecto1evaluacion.modelos.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProveedoresDTO {

    private Integer numVoluntarios;

    private String sede;

    private String ubicacion;

    private String cif;

    private Integer id_usuario;
}
