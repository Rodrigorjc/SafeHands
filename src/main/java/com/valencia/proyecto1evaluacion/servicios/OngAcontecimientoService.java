package com.valencia.proyecto1evaluacion.servicios;

import com.valencia.proyecto1evaluacion.dtos.AcontecimientoDTO;
import com.valencia.proyecto1evaluacion.modelos.Acontecimiento;
import com.valencia.proyecto1evaluacion.modelos.OngAcontecimiento;
import com.valencia.proyecto1evaluacion.repositorio.OngAcontecimientoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class OngAcontecimientoService {

    private final OngAcontecimientoRepository ongAcontecimientoRepository;

    public List<AcontecimientoDTO> obtenerAcontecimientosPorOng(Integer ongId) {
        List<OngAcontecimiento> ongAcontecimientos = ongAcontecimientoRepository.findByOngId(ongId);
        List<AcontecimientoDTO> acontecimientos = new ArrayList<>();
        for (OngAcontecimiento ongAcontecimiento : ongAcontecimientos) {
            Acontecimiento acontecimiento = ongAcontecimiento.getAcontecimiento();
            AcontecimientoDTO dto = new AcontecimientoDTO();
            dto.setId(acontecimiento.getId());
            dto.setNombre(acontecimiento.getNombre());
            dto.setDescripcion(acontecimiento.getDescripcion());
            dto.setImg(acontecimiento.getImg());
            dto.setUbicacion(acontecimiento.getUbicacion());
            acontecimientos.add(dto);
        }
        return acontecimientos;
    }
}
