package com.valencia.proyecto1evaluacion.servicios;

import com.valencia.proyecto1evaluacion.dtos.AcontecimientoCrearDTO;
import com.valencia.proyecto1evaluacion.dtos.AcontecimientoDTO;
import com.valencia.proyecto1evaluacion.modelos.Acontecimiento;
import com.valencia.proyecto1evaluacion.repositorios.AcontecimientoRepositorio;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AcontecimientoService {
    private AcontecimientoRepositorio acontecimientoRepositorio;

    /**
     * Devuelve todos los acontecimientos
     *
     * @return
     */
    public List<Acontecimiento> getAll(){
        return acontecimientoRepositorio.findAll();
    }

    /**
     * Busca un acontecimiento por id
     *
     * @param id
     * @return
     */
    public Acontecimiento getById(Integer id){
        return acontecimientoRepositorio.findById(id).orElse(null);
    }

    /**
     * Crea un nuevo acontecimiento
     *
     * @param acontecimientoCrearDTO
     * @return
     */
    public Acontecimiento crearNuevo(AcontecimientoCrearDTO acontecimientoCrearDTO){
        Acontecimiento entity = new Acontecimiento();
        entity.setNombre(acontecimientoCrearDTO.getNombre());
        entity.setDescripcion(acontecimientoCrearDTO.getDescripcion());
        entity.setUbicacion(acontecimientoCrearDTO.getUbicacion());

        return acontecimientoRepositorio.save(entity);
    }

    /**
     * Edita un acontecimiento
     *
     * @param dto
     * @param id
     * @return
     */
    public Acontecimiento editar(AcontecimientoCrearDTO dto, Integer id){
        Acontecimiento entity = acontecimientoRepositorio.getReferenceById(id);
        entity.setNombre(dto.getNombre());
        entity.setDescripcion(dto.getDescripcion());
        entity.setUbicacion(dto.getUbicacion());

        return acontecimientoRepositorio.save(entity);
    }

    /**
     * Guarda un acontecimiento
     *
     * @param dto
     * @return
     */
    public Acontecimiento guardar(AcontecimientoCrearDTO dto){
        Acontecimiento entity = new Acontecimiento();
        entity.setNombre(dto.getNombre());
        entity.setDescripcion(dto.getDescripcion());
        entity.setUbicacion(dto.getUbicacion());

        return acontecimientoRepositorio.save(entity);
    }

    /**
     * Elimina un acontecimiento
     *
     * @param id
     */
    public String eliminar(Integer id){
        String mensaje;
        Acontecimiento acontecimiento = getById(id);

        if(acontecimiento == null){
            mensaje = "El acontecimiento con el id que está buscando no existe.";
    }
        try{
            acontecimientoRepositorio.deleteById(id);

            acontecimiento = getById(id);
            if (acontecimiento != null) {
                mensaje = "No se ha podido eliminar el acontecimiento.";
            }else {
                mensaje = "Acontecimiento eliminado correctamente.";
            }
        }catch (Exception e){
            mensaje = "No se ha podido eliminar el acontecimiento.";
        }
        return mensaje;


    }
}
