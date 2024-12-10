package com.valencia.proyecto1evaluacion.dtos;


import com.valencia.proyecto1evaluacion.modelos.Ong;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AcontecimientoDTO {
    private Integer id;
    private String nombre;
    private String descripcion;
    private String ubicacion;
    private String img;
    private Integer idOng;

    public Integer getOngId() {
        return idOng;
    }
}
