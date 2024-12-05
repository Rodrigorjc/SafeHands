package com.valencia.proyecto1evaluacion.servicios;

import com.valencia.proyecto1evaluacion.dtos.AcontecimientoDTO;
import com.valencia.proyecto1evaluacion.dtos.AcontecimientoOngVincularDTO;
import com.valencia.proyecto1evaluacion.dtos.OngDTO;
import com.valencia.proyecto1evaluacion.modelos.Acontecimiento;
import com.valencia.proyecto1evaluacion.modelos.Ong;
import com.valencia.proyecto1evaluacion.modelos.OngAcontecimiento;
import com.valencia.proyecto1evaluacion.repositorio.OngAcontecimientoRepository;
import com.valencia.proyecto1evaluacion.repositorio.OngRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class OngAcontecimientoService {

    private final OngAcontecimientoRepository ongAcontecimientoRepository;
    private final OngRepository ongRepositorio;

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

    public void eliminarAcontecimientosAsociados(Integer idOng, Integer idAcontecimiento) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nombre = authentication.getName();
        Ong ong = ongRepositorio.findByUsuarioUsername(nombre)
                .orElseThrow(() -> new SecurityException("ONG no encontrada o no autorizada"));

        if (!ong.getId().equals(idOng)) {
            throw new SecurityException("No tienes permiso para eliminar estos acontecimientos");
        }

        List<OngAcontecimiento> ongAcontecimientos = ongAcontecimientoRepository.findByOngId(idOng);
        for (OngAcontecimiento oa : ongAcontecimientos) {
            if (oa.getAcontecimiento().getId().equals(idAcontecimiento)) {
                ongAcontecimientoRepository.delete(oa);
            }
        }
    }
    public List<OngDTO> obtenerOngsPorAcontecimiento(Integer acontecimientoId) {
        List<OngAcontecimiento> ongAcontecimientos = ongAcontecimientoRepository.findByAcontecimientoId(acontecimientoId);
        List<OngDTO> ongs = new ArrayList<>();
        for (OngAcontecimiento ongAcontecimiento : ongAcontecimientos) {
            Ong ong = ongAcontecimiento.getOng();
            OngDTO dto = new OngDTO();
            dto.setId(ong.getId());
            dto.setNumVoluntarios(ong.getNumVoluntarios());
            dto.setSede(ong.getSede());
            dto.setDescripcion(ong.getDescripcion());
            dto.setUbicacion(ong.getUbicacion());
            dto.setImg(ong.getImg());
            dto.setEmail(ong.getUsuario().getEmail());
            dto.setUsername(ong.getUsuario().getUsername());
            dto.setIdUsuario(ong.getUsuario().getId());
            ongs.add(dto);
        }
        return ongs;
    }


}
