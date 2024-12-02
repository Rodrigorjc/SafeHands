package com.valencia.proyecto1evaluacion.servicios;

import com.valencia.proyecto1evaluacion.dtos.AcontecimientoCrearDTO;
import com.valencia.proyecto1evaluacion.dtos.AcontecimientoDTO;
import com.valencia.proyecto1evaluacion.dtos.AcontecimientoInfoDTO;
import com.valencia.proyecto1evaluacion.enums.Rol;
import com.valencia.proyecto1evaluacion.modelos.Acontecimiento;
import com.valencia.proyecto1evaluacion.modelos.OngAcontecimiento;
import com.valencia.proyecto1evaluacion.modelos.Usuario;
import com.valencia.proyecto1evaluacion.repositorio.AcontecimientoRepository;
import com.valencia.proyecto1evaluacion.repositorio.OngAcontecimientoRepository;
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
    private UsuarioService usuarioService;

    /**
     * Devuelve todos los acontecimientos
     *
     * @return
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
    public Acontecimiento getById(Integer id){
        return acontecimientoRepository.findById(id).orElse(null);
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

        return acontecimientoRepository.save(entity);
    }

    /**
     * Edita un acontecimiento
     *
     * @param dto
     * @param id
     * @return
     */
    public Acontecimiento editar(AcontecimientoCrearDTO dto, Integer id){
        Acontecimiento entity = acontecimientoRepository.getReferenceById(id);
        entity.setNombre(dto.getNombre());
        entity.setDescripcion(dto.getDescripcion());
        entity.setUbicacion(dto.getUbicacion());

        return acontecimientoRepository.save(entity);
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

        return acontecimientoRepository.save(entity);
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
            acontecimientoRepository.deleteById(id);

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
        acontecimiento.setId(acontecimientoDTO.getId());
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
