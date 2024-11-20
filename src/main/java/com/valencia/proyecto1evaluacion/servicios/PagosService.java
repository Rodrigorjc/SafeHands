package com.valencia.proyecto1evaluacion.servicios;

import com.valencia.proyecto1evaluacion.dtos.PagosDTO;
import com.valencia.proyecto1evaluacion.repositorio.PagosRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;


@Service
@AllArgsConstructor
public class PagosService {

    private PagosRepository pagosRepository;

    public List<PagosDTO> findTotalDonaciones() {
        Double totalDonaciones = pagosRepository.findTotalDonaciones();
        return Collections.singletonList(new PagosDTO(totalDonaciones));
    }


}
