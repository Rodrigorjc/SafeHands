package com.valencia.proyecto1evaluacion.servicios;

import com.valencia.proyecto1evaluacion.dtos.PerfilProveedorCrearDTO;
import com.valencia.proyecto1evaluacion.dtos.PerfilProveedoresDTO;
import com.valencia.proyecto1evaluacion.mappers.PerfilMapper;
import com.valencia.proyecto1evaluacion.modelos.PerfilProveedores;
import com.valencia.proyecto1evaluacion.modelos.Proveedores;
import com.valencia.proyecto1evaluacion.repositorio.ProveedoresRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ProveedoresService {

    private ProveedoresRepository proveedoresRepository;
    private PerfilMapper perfilMapper;

    /**
     * Este método extrae todos los perfiles de base de datos
     *
     * @return
     */
    public List<PerfilProveedoresDTO> getAll(){
        List<Proveedores> proveedores = proveedoresRepository.findAll();
        List<PerfilProveedoresDTO> perfilDTOS = new ArrayList<>();

        for (Proveedores p: proveedores){
            PerfilProveedoresDTO dto = new PerfilProveedoresDTO();
            dto.setNombre(p.getNombre());
            dto.setDescripcion(p.getDescripcion());
            dto.setUrl(p.getUrl());
            dto.setNumVoluntarios(p.getNumVoluntarios());
            dto.setSede(p.getSede());
            dto.setCif(p.getCif());
            dto.setUbicacion(p.getUbicacion());

            perfilDTOS.add(dto);
        }
        return perfilDTOS;
    }

    /**
     * Busca perfiles por coincidencia en nombre, descripcion o sede
     *
     * @param busqueda
     * @return
     */
    public List<PerfilProveedoresDTO> buscar(String busqueda){
        return perfilMapper.toDTO(proveedoresRepository.buscar(busqueda));
    }

    /**
     * Este método busca un proveedor a partir de su id
     *
     * @param id
     * @return
     */
    public Proveedores getById(Integer id){
        return proveedoresRepository.findById(id).orElse(null);
    }

    /**
     * Este método guarda un perfilProveedor nuevo o modifica uno existente
     *
     * @param dto
     * @return
     */
    public Proveedores guardar(PerfilProveedorCrearDTO dto){
        Proveedores perfilGuardar = new Proveedores();
        perfilGuardar.setNombre(dto.getNombre());
        perfilGuardar.setDescripcion(dto.getDescripcion());
        perfilGuardar.setUrl(dto.getUrl());
        perfilGuardar.setNumVoluntarios(dto.getNumVoluntarios());
        perfilGuardar.setSede(dto.getSede());
        perfilGuardar.setCif(dto.getCif());
        perfilGuardar.setUbicacion(dto.getUbicacion());

        return proveedoresRepository.save(perfilGuardar);
    }

    /**
     * Elimina un perfilProveedor a traves de su id
     *
     * @param id
     */
    public String eliminar(Integer id){
        String mensaje;
        Proveedores proveedores = getById(id);

        if(proveedores == null){
            return "El perfil del proveedor con el id indicado no existe";
        }

        try{
            proveedoresRepository.deleteById(id);
            proveedores = getById(id);
            if(proveedores == null){
                mensaje = "El perfil del proveedor no se ha podido eliminar.";
        }else{
                mensaje = "El perfil del proveedor se ha eliminado correctamente.";
            }
}catch (Exception e){
            mensaje = "El perfil del proveedor no se ha podido eliminar.";
        }
        return mensaje;
        }

        public void eliminar(Proveedores proveedor){
        proveedoresRepository.delete(proveedor);
    }
}
