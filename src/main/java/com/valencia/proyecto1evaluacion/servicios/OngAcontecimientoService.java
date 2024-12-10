package com.valencia.proyecto1evaluacion.servicios;

import com.valencia.proyecto1evaluacion.dtos.AcontecimientoDTO;
import com.valencia.proyecto1evaluacion.dtos.AcontecimientoOngVincularDTO;
import com.valencia.proyecto1evaluacion.dtos.OngDTO;
import com.valencia.proyecto1evaluacion.enums.Rol;
import com.valencia.proyecto1evaluacion.modelos.Acontecimiento;
import com.valencia.proyecto1evaluacion.modelos.Ong;
import com.valencia.proyecto1evaluacion.modelos.OngAcontecimiento;
import com.valencia.proyecto1evaluacion.modelos.Usuario;
import com.valencia.proyecto1evaluacion.repositorio.AcontecimientoRepository;
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
    private final UsuarioService usuarioService;
    private final AcontecimientoRepository acontecimientoRepository;

    /**
     * Metodo para obtener los acontecimientos asociados a una ONG
     * @param ongId
     * @return List<AcontecimientoDTO>
     */

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

    /**
     * metodo para eliminar un acontecimiento asociado a una ONG
     * @param idOng
     * @param idAcontecimiento
     */

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

    /**
     * metodo para obtener ongs asociadas por acontecimiento
     * @param acontecimientoId
     * @return List<OngDTO>
     */
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

    /**
     * Metodo para vincular un acontecimiento a una ONG
     * @param acontecimientoId
     * @return AcontecimientoOngVincularDTO
     */
    public AcontecimientoOngVincularDTO  acontecimientoOngVincular(Integer acontecimientoId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nombre = authentication.getName();
        Usuario usuario = usuarioService.buscarUsuarioPorNombre(nombre);

        if (usuario == null || !usuario.getRol().equals(Rol.ONG)) {
            throw new SecurityException("No tienes permiso para vincular acontecimientos a ONGs");
        }

        Ong ong = ongRepositorio.findByUsuarioId(usuario.getId())
                .orElseThrow(() -> new RuntimeException("ONG no encontrada"));

        Acontecimiento acontecimiento = acontecimientoRepository.findById(acontecimientoId)
                .orElseThrow(() -> new RuntimeException("Acontecimiento no encontrado"));

        boolean exists = ongAcontecimientoRepository.existsByOngAndAcontecimiento(ong, acontecimiento);
        if (exists) {
            throw new RuntimeException("La ONG ya está vinculada a este acontecimiento");
        }

        OngAcontecimiento ongAcontecimiento = new OngAcontecimiento();
        ongAcontecimiento.setOng(ong);
        ongAcontecimiento.setAcontecimiento(acontecimiento);
        ongAcontecimientoRepository.save(ongAcontecimiento);

        AcontecimientoOngVincularDTO dto = new AcontecimientoOngVincularDTO();
        dto.setIdAcontecimiento(acontecimiento.getId());
        dto.setIdOng(ong.getId());

        return dto;
    }



}
