package com.valencia.proyecto1evaluacion.mappers;

import com.valencia.proyecto1evaluacion.dtos.PerfilProveedoresDTO;
import com.valencia.proyecto1evaluacion.modelos.Proveedores;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PerfilMapper {
    /**
     * Este método sirve para pasar a Entity un DTO de Perfil
     * @param dto
     * @return
     */
    Proveedores toEntity(PerfilProveedoresDTO dto);

    /**
     * Este método sirve para pasar a DTO un Entity de Perfil
     * @param entity
     * @return
     */
    PerfilProveedoresDTO toDTO(Proveedores entity);

    List<Proveedores> toEntity(List<PerfilProveedoresDTO> dtos);

    List<PerfilProveedoresDTO> toDTO(List<Proveedores> entities);
}
