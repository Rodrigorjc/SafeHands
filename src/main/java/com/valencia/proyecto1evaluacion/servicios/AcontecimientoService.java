package com.valencia.proyecto1evaluacion.servicios;

import com.valencia.proyecto1evaluacion.dtos.AcontecimientoCrearDTO;
import com.valencia.proyecto1evaluacion.dtos.AcontecimientoDTO;
import com.valencia.proyecto1evaluacion.dtos.AcontecimientoInfoDTO;
import com.valencia.proyecto1evaluacion.dtos.ConsultaAcontecimientoDTO;
import com.valencia.proyecto1evaluacion.enums.Rol;
import com.valencia.proyecto1evaluacion.modelos.Acontecimiento;
import com.valencia.proyecto1evaluacion.modelos.OngAcontecimiento;
import com.valencia.proyecto1evaluacion.modelos.Usuario;
import com.valencia.proyecto1evaluacion.repositorio.AcontecimientoRepository;
import com.valencia.proyecto1evaluacion.repositorio.OngAcontecimientoRepository;
import com.valencia.proyecto1evaluacion.repositorio.PagosRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private UsuarioService usuarioService;

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
     * @return AcontecimientoDTO
     */
    public AcontecimientoDTO getById(Integer id){
        Acontecimiento acontecimiento = acontecimientoRepository.findById(id).orElse(null);
        AcontecimientoDTO acontecimientoDTO = new AcontecimientoDTO();
        assert acontecimiento != null;
        acontecimientoDTO.setUbicacion(acontecimiento.getUbicacion());
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

    public AcontecimientoDTO obtenerAcontecimientoPorId(Integer id) {
        Acontecimiento acontecimiento = acontecimientoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Acontecimiento no encontrado"));
        AcontecimientoDTO dto = new AcontecimientoDTO();
        dto.setId(acontecimiento.getId());
        dto.setNombre(acontecimiento.getNombre());
        dto.setDescripcion(acontecimiento.getDescripcion());
        dto.setUbicacion(acontecimiento.getUbicacion());
        dto.setImg(acontecimiento.getImg());
        return dto;
    }

    public AcontecimientoDTO crearNuevoAcontecimiento(AcontecimientoDTO acontecimientoDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nombre = authentication.getName();
        Usuario usuario = usuarioService.buscarUsuarioPorNombre(nombre);

        if (usuario == null || !(usuario.getRol().equals(Rol.ONG) || usuario.getRol().equals(Rol.ADMIN))) {
            throw new SecurityException("No tienes permiso para crear acontecimientos");
        }

        Acontecimiento acontecimiento = new Acontecimiento();
//        acontecimiento.setId(acontecimientoDTO.getId());
        acontecimiento.setNombre(acontecimientoDTO.getNombre());
        acontecimiento.setDescripcion(acontecimientoDTO.getDescripcion());
        acontecimiento.setUbicacion(acontecimientoDTO.getUbicacion());
        acontecimiento.setImg(acontecimientoDTO.getImg());

        acontecimientoRepository.save(acontecimiento);
        return acontecimientoDTO;
    }

    public String eliminarAcontecimiento(Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nombre = authentication.getName();
        Usuario usuario = usuarioService.buscarUsuarioPorNombre(nombre);

        if (usuario == null || !(usuario.getRol().equals(Rol.ONG) || usuario.getRol().equals(Rol.ADMIN))) {
            throw new SecurityException("No tienes permiso para eliminar acontecimientos");
        }

        Acontecimiento acontecimiento = acontecimientoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Acontecimiento no encontrado"));

        acontecimientoRepository.delete(acontecimiento);
        return "Acontecimiento eliminado correctamente.";
    }

    public Acontecimiento editarAcontecimiento(Integer id, AcontecimientoDTO acontecimientoDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nombre = authentication.getName();
        Usuario usuario = usuarioService.buscarUsuarioPorNombre(nombre);

        if (usuario == null || !(usuario.getRol().equals(Rol.ADMIN) || usuario.getRol().equals(Rol.ONG))) {
            throw new SecurityException("No tienes permiso para editar acontecimientos");
        }

        Acontecimiento acontecimiento = acontecimientoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Acontecimiento no encontrado"));

        acontecimiento.setNombre(acontecimientoDTO.getNombre());
        acontecimiento.setDescripcion(acontecimientoDTO.getDescripcion());
        acontecimiento.setUbicacion(acontecimientoDTO.getUbicacion());
        acontecimiento.setImg(acontecimientoDTO.getImg());

        return acontecimientoRepository.save(acontecimiento);
    }


}

