package com.valencia.proyecto1evaluacion.servicios;

import com.valencia.proyecto1evaluacion.dtos.AcontecimientoCrearDTO;
import com.valencia.proyecto1evaluacion.dtos.AcontecimientoDTO;
import com.valencia.proyecto1evaluacion.dtos.AcontecimientoInfoDTO;
import com.valencia.proyecto1evaluacion.dtos.ConsultaAcontecimientoDTO;
import com.valencia.proyecto1evaluacion.modelos.Acontecimiento;
import com.valencia.proyecto1evaluacion.modelos.Ong;
import com.valencia.proyecto1evaluacion.repositorio.AcontecimientoRepository;
import com.valencia.proyecto1evaluacion.repositorio.OngAcontecimientoRepository;
import com.valencia.proyecto1evaluacion.repositorio.PagosRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AcontecimientoService {
    private AcontecimientoRepository acontecimientoRepository;
    private OngAcontecimientoRepository ongAcontecimientoRepository;
    private PagosRepository pagosRepository;


    /**
     * Devuelve todos los acontecimientos
     *
     * @return acontecimientosDTO
     */
    public List<AcontecimientoDTO> getAll(){
        List<Acontecimiento> acontecimientos = acontecimientoRepository.findAll();
        List<AcontecimientoDTO> acontecimientoDTOS = new ArrayList<>();
        for (Acontecimiento a : acontecimientos ) {
            AcontecimientoDTO acontecimientoDTO = new AcontecimientoDTO();
            acontecimientoDTO.setId(a.getId());
            acontecimientoDTO.setNombre(a.getNombre());
            acontecimientoDTO.setDescripcion(a.getDescripcion());
            acontecimientoDTO.setImg(a.getImg());
            acontecimientoDTO.setUbicacion(a.getUbicacion());
            acontecimientoDTOS.add(acontecimientoDTO);
        }
        return acontecimientoDTOS;
    }

    /**
     * Busca un acontecimiento por id
     *
     * @param id
     * @return
     */
    public AcontecimientoDTO getById(Integer id){
        Acontecimiento acontecimiento = acontecimientoRepository.findById(id).orElse(null);
        AcontecimientoDTO acontecimientoDTO = new AcontecimientoDTO();
        assert acontecimiento != null;
        acontecimientoDTO.setUbicacion(acontecimiento.getUbicacion());
        acontecimientoDTO.setIdOng(acontecimiento.getOng().getId());
        acontecimientoDTO.setNombre(acontecimiento.getNombre());
        acontecimientoDTO.setDescripcion(acontecimiento.getDescripcion());
        acontecimientoDTO.setImg(acontecimiento.getImg());
        acontecimientoDTO.setId(acontecimiento.getId());

        return acontecimientoDTO;
    }

    /**
     * Crea un nuevo acontecimiento
     *
     * @param acontecimientoCrearDTO
     * @return
     */

    public Acontecimiento crearNuevo(AcontecimientoCrearDTO acontecimientoCrearDTO) {
        Acontecimiento entity = new Acontecimiento();
        entity.setNombre(acontecimientoCrearDTO.getNombre());
        entity.setDescripcion(acontecimientoCrearDTO.getDescripcion());
        entity.setUbicacion(acontecimientoCrearDTO.getUbicacion());
        entity.setImg(acontecimientoCrearDTO.getImg());
        return acontecimientoRepository.save(entity);
    }

    /**
     * Edita un acontecimiento
     *
     * @param acontecimientoDTO
     * @param id
     * @return
     */
    public AcontecimientoDTO editar(AcontecimientoDTO acontecimientoDTO, Integer id) {
        Acontecimiento acontecimiento = acontecimientoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Acontecimiento not found"));

        acontecimiento.setNombre(acontecimientoDTO.getNombre());
        acontecimiento.setDescripcion(acontecimientoDTO.getDescripcion());
        acontecimiento.setImg(acontecimientoDTO.getImg());
        acontecimiento.setUbicacion(acontecimientoDTO.getUbicacion());


        acontecimientoRepository.save(acontecimiento);

        return acontecimientoDTO;
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
        entity.setImg(dto.getImg());

        return acontecimientoRepository.save(entity);
    }

    /**
     * Elimina un acontecimiento
     *
     * @param id
     */
    public String eliminar(Integer id) {
        Acontecimiento acontecimiento = acontecimientoRepository.findById(id).orElse(null);
        if (acontecimiento == null) {
            return "El acontecimiento con el id que está buscando no existe.";
        }
        try {
            acontecimientoRepository.deleteById(id);
            return "Acontecimiento eliminado correctamente.";
        } catch (Exception e) {
            return "No se ha podido eliminar el acontecimiento.";
        }
    }



    public AcontecimientoDTO crearAcontecimiento(AcontecimientoDTO acontecimientoDTO) {
        Acontecimiento acontecimiento = new Acontecimiento();
        acontecimiento.setId(acontecimientoDTO.getId());
        acontecimiento.setNombre(acontecimientoDTO.getNombre());
        acontecimiento.setDescripcion(acontecimientoDTO.getDescripcion());
        acontecimiento.setUbicacion(acontecimientoDTO.getUbicacion());
        acontecimiento.setImg(acontecimientoDTO.getImg());
        acontecimientoRepository.save(acontecimiento);
        return acontecimientoDTO;
    }



    public List<ConsultaAcontecimientoDTO> findTotalRecaudadoPorAcontecimiento() {
        List<Object[]> rawResults = acontecimientoRepository.findTotalRecaudadoPorAcontecimientoRaw();

        return rawResults.stream()
                .map(result -> ConsultaAcontecimientoDTO.builder()
                        .nombre((String) result[0])  // Mapeo del campo "nombre" del acontecimiento
                        .totalRecaudado(((Number) result[1]).floatValue())  // Mapeo de "total_recaudado"
                        .build())
                .collect(Collectors.toList());
    }





    public Double findTotalDonaciones() {
        return pagosRepository.findTotalDonaciones();
    }

//    public List<Acontecimiento> obtenerAcontecimientosPorOng(Integer ongId) {
//        List<OngAcontecimiento> ongAcontecimientos = ongAcontecimientoRepository.findByOngId(ongId);
//        List<Acontecimiento> acontecimientos = new ArrayList<>();
//        for (OngAcontecimiento ongAcontecimiento : ongAcontecimientos) {
//            acontecimientos.add(ongAcontecimiento.getAcontecimiento());
//        }
//        return acontecimientos;
//
//    }

    public List<AcontecimientoInfoDTO> obtenerInfoAcontecimiento() {
        return acontecimientoRepository.obtenerTotalesAcontecimientos();
    }
}

