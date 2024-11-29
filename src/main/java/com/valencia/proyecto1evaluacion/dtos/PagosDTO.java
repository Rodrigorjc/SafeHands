package com.valencia.proyecto1evaluacion.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PagosDTO {
    private Double totalDonaciones;
    private Integer id;
    private Float cuantia;
    private Boolean estado;

    public PagosDTO(Double totalDonaciones) {
        this.totalDonaciones = totalDonaciones;
    }
}
