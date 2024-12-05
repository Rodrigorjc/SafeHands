package com.valencia.proyecto1evaluacion.servicios;

import com.valencia.proyecto1evaluacion.dtos.AcontecimientoCrearDTO;
import com.valencia.proyecto1evaluacion.dtos.AcontecimientoDTO;
import com.valencia.proyecto1evaluacion.dtos.AcontecimientoInfoDTO;
import com.valencia.proyecto1evaluacion.dtos.ConsultaAcontecimientoDTO;
import com.valencia.proyecto1evaluacion.enums.Rol;
import com.valencia.proyecto1evaluacion.modelos.Acontecimiento;
import com.valencia.proyecto1evaluacion.modelos.Usuario;
import com.valencia.proyecto1evaluacion.repositorio.AcontecimientoRepository;
import com.valencia.proyecto1evaluacion.repositorio.OngAcontecimientoRepository;
import com.valencia.proyecto1evaluacion.repositorio.PagosRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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
     * Este método se encarga de recuperar todos los registros de acontecimientos desde la base de datos
     * y convertirlos en objetos AcontecimientoDTO, que son utilizados para transferir datos de manera
     * más eficiente y segura entre las capas de la aplicación.
     *
     * @return Una lista de objetos AcontecimientoDTO que representan todos los acontecimientos almacenados en la base de datos.
     */
    public List<AcontecimientoDTO> getAll() {
        List<Acontecimiento> acontecimientos = acontecimientoRepository.findAll();
        return acontecimientos.stream()
                .map(acontecimiento -> {
                    AcontecimientoDTO dto = new AcontecimientoDTO();
                    dto.setId(acontecimiento.getId());
                    dto.setNombre(acontecimiento.getNombre());
                    dto.setDescripcion(acontecimiento.getDescripcion());
                    dto.setUbicacion(acontecimiento.getUbicacion());
                    dto.setImg(acontecimiento.getImg());
//                    dto.setOngId(acontecimiento.getOng().getId());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    /**
     * Este método busca un acontecimiento en la base de datos utilizando su identificador único (ID).
     * Si el acontecimiento es encontrado, se convierte en un objeto AcontecimientoDTO y se devuelve.
     * Si no se encuentra, se devuelve null.
     *
     * @param id El identificador único del acontecimiento que se desea buscar.
     * @return Un objeto AcontecimientoDTO que representa el acontecimiento encontrado, o null si no se encuentra.
     */
    public AcontecimientoDTO getById(Integer id){
        // Busca el acontecimiento en el repositorio utilizando el ID proporcionado
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
     * Este método se encarga de crear un nuevo acontecimiento en la base de datos.
     * Recibe un objeto AcontecimientoCrearDTO que contiene la información necesaria para crear el acontecimiento.
     * Luego, convierte este objeto DTO en una entidad Acontecimiento y lo guarda en la base de datos.
     *
     * @param acontecimientoCrearDTO Un objeto AcontecimientoCrearDTO que contiene los datos del nuevo acontecimiento.
     * @return La entidad Acontecimiento que ha sido guardada en la base de datos.
     */

    public Acontecimiento crearNuevo(AcontecimientoCrearDTO acontecimientoCrearDTO) {
        // Crea una nueva instancia de la entidad Acontecimiento
        Acontecimiento entity = new Acontecimiento();

        // Asigna los valores del DTO a la entidad
        entity.setNombre(acontecimientoCrearDTO.getNombre());
        entity.setDescripcion(acontecimientoCrearDTO.getDescripcion());
        entity.setUbicacion(acontecimientoCrearDTO.getUbicacion());
        entity.setImg(acontecimientoCrearDTO.getImg());
        // Guarda la entidad en la base de datos y la devuelve
        return acontecimientoRepository.save(entity);
    }

    /**
     * Este método se encarga de editar un acontecimiento existente en la base de datos.
     * Recibe un objeto AcontecimientoDTO que contiene la nueva información del acontecimiento
     * y el identificador único (ID) del acontecimiento que se desea editar.
     * Si el acontecimiento es encontrado, se actualizan sus propiedades con los valores del DTO
     * y se guarda en la base de datos.
     *
     * @param acontecimientoDTO Un objeto AcontecimientoDTO que contiene los nuevos datos del acontecimiento.
     * @param id El identificador único del acontecimiento que se desea editar.
     * @return Un objeto AcontecimientoDTO que representa el acontecimiento actualizado.
     */
    public AcontecimientoDTO editar(AcontecimientoDTO acontecimientoDTO, Integer id) {
        // Busca el acontecimiento en el repositorio utilizando el ID proporcionado
        Acontecimiento acontecimiento = acontecimientoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Acontecimiento not found"));

        // Actualiza las propiedades del acontecimiento con los valores del DTO
        acontecimiento.setNombre(acontecimientoDTO.getNombre());
        acontecimiento.setDescripcion(acontecimientoDTO.getDescripcion());
        acontecimiento.setImg(acontecimientoDTO.getImg());
        acontecimiento.setUbicacion(acontecimientoDTO.getUbicacion());

        // Guarda el acontecimiento actualizado en la base de datos
        acontecimientoRepository.save(acontecimiento);
        // Devuelve el objeto AcontecimientoDTO actualizado
        return acontecimientoDTO;
    }

    /**
     * Este método se encarga de guardar un nuevo acontecimiento en la base de datos.
     * Recibe un objeto AcontecimientoCrearDTO que contiene la información necesaria para crear el acontecimiento.
     * Luego, convierte este objeto DTO en una entidad Acontecimiento y lo guarda en la base de datos.
     *
     * @param dto Un objeto AcontecimientoCrearDTO que contiene los datos del nuevo acontecimiento.
     * @return La entidad Acontecimiento que ha sido guardada en la base de datos.
     */
    public Acontecimiento guardar(AcontecimientoCrearDTO dto){
        Acontecimiento entity = new Acontecimiento();

        // Asigna los valores del DTO a la entidad
        entity.setNombre(dto.getNombre());
        entity.setDescripcion(dto.getDescripcion());
        entity.setUbicacion(dto.getUbicacion());
        entity.setImg(dto.getImg());

        return acontecimientoRepository.save(entity);
    }

    /**
     * Este método se encarga de eliminar un acontecimiento existente en la base de datos.
     * Recibe el identificador único (ID) del acontecimiento que se desea eliminar.
     * Si el acontecimiento es encontrado, se elimina de la base de datos.
     * Si no se encuentra, se devuelve un mensaje indicando que el acontecimiento no existe.
     *
     * @param id El identificador único del acontecimiento que se desea eliminar.
     * @return Un mensaje indicando el resultado de la operación de eliminación.
     */

    public String eliminar(Integer id) {
        // Busca el acontecimiento en el repositorio utilizando el ID proporcionado
        Acontecimiento acontecimiento = acontecimientoRepository.findById(id).orElse(null);

        // Si el acontecimiento no se encuentra, devuelve un mensaje indicando que no existe
        if (acontecimiento == null) {
            return "El acontecimiento con el id que está buscando no existe.";
        }
        try {
            // Elimina el acontecimiento del repositorio
            acontecimientoRepository.deleteById(id);
            return "Acontecimiento eliminado correctamente.";
        } catch (Exception e) {
            // Si ocurre un error durante la eliminación, devuelve un mensaje indicando el fallo
            return "No se ha podido eliminar el acontecimiento.";
        }
    }


    /**
     * Este método se encarga de crear un nuevo acontecimiento en la base de datos.
     * Recibe un objeto AcontecimientoDTO que contiene la información necesaria para crear el acontecimiento.
     * Luego, convierte este objeto DTO en una entidad Acontecimiento y lo guarda en la base de datos.
     *
     * @param acontecimientoDTO Un objeto AcontecimientoDTO que contiene los datos del nuevo acontecimiento.
     * @return Un objeto AcontecimientoDTO que representa el acontecimiento creado.
     */
    public AcontecimientoDTO crearAcontecimiento(AcontecimientoDTO acontecimientoDTO) {
        Acontecimiento acontecimiento = new Acontecimiento();

        // Asigna los valores del DTO a la entidad
        acontecimiento.setId(acontecimientoDTO.getId());
        acontecimiento.setNombre(acontecimientoDTO.getNombre());
        acontecimiento.setDescripcion(acontecimientoDTO.getDescripcion());
        acontecimiento.setUbicacion(acontecimientoDTO.getUbicacion());
        acontecimiento.setImg(acontecimientoDTO.getImg());

        acontecimientoRepository.save(acontecimiento);
        // Devuelve el objeto AcontecimientoDTO
        return acontecimientoDTO;
    }


    /**
     * Este método se encarga de obtener el total recaudado por cada acontecimiento.
     * Realiza una consulta a la base de datos para obtener los resultados en bruto,
     * luego los mapea a objetos ConsultaAcontecimientoDTO y los devuelve en una lista.
     *
     * @return Una lista de objetos ConsultaAcontecimientoDTO que representan el total recaudado por cada acontecimiento.
     */
    public List<ConsultaAcontecimientoDTO> findTotalRecaudadoPorAcontecimiento() {
        // Realiza la consulta a la base de datos para obtener los resultados en bruto
        List<Object[]> rawResults = acontecimientoRepository.findTotalRecaudadoPorAcontecimientoRaw();

        // Mapea los resultados en bruto a objetos ConsultaAcontecimientoDTO y los devuelve en una lista
        return rawResults.stream()
                .map(result -> ConsultaAcontecimientoDTO.builder()
                        .nombre((String) result[0])  // Mapeo del campo "nombre" del acontecimiento
                        .totalRecaudado(((Number) result[1]).floatValue())  // Mapeo de "total_recaudado"
                        .build())
                .collect(Collectors.toList());
    }




    /**
     * Este método se encarga de obtener el total de donaciones registradas en la base de datos.
     * Realiza una consulta al repositorio de pagos para obtener la suma total de las donaciones.
     *
     * @return Un valor Double que representa el total de donaciones.
     */
    public Double findTotalDonaciones() {

        // Realiza la consulta al repositorio de pagos para obtener el total de donaciones
        return pagosRepository.findTotalDonaciones();
    }



//    /**
//     * Este método se encarga de obtener una lista de acontecimientos asociados a una ONG específica.
//     * Recibe el identificador único (ID) de la ONG y busca todos los registros de OngAcontecimiento
//     * que están asociados a esa ONG. Luego, extrae los acontecimientos de estos registros y los devuelve en una lista.
//     *
//     * @param ongId El identificador único de la ONG cuyos acontecimientos se desean obtener.
//     * @return Una lista de objetos Acontecimiento que representan los acontecimientos asociados a la ONG.
//     */
//    public List<Acontecimiento> obtenerAcontecimientosPorOng(Integer ongId) {
//        List<OngAcontecimiento> ongAcontecimientos = ongAcontecimientoRepository.findByOngId(ongId);
//        List<Acontecimiento> acontecimientos = new ArrayList<>();
//        for (OngAcontecimiento ongAcontecimiento : ongAcontecimientos) {
//            acontecimientos.add(ongAcontecimiento.getAcontecimiento());
//        }
//        return acontecimientos;
//
//    }



    /**
     * Este método se encarga de obtener información detallada de los acontecimientos.
     * Realiza una consulta al repositorio de acontecimientos para obtener los totales de los acontecimientos
     * y los devuelve en una lista de objetos AcontecimientoInfoDTO.
     *
     * @return Una lista de objetos AcontecimientoInfoDTO que representan la información detallada de los acontecimientos.
     */
    public List<AcontecimientoInfoDTO> obtenerInfoAcontecimiento() {
        // Realiza la consulta al repositorio de acontecimientos para obtener los totales de los acontecimientos
        return acontecimientoRepository.obtenerTotalesAcontecimientos();
    }


    /**
     * Este método busca un acontecimiento en la base de datos utilizando su identificador único (ID).
     * Si el acontecimiento es encontrado, se convierte en un objeto AcontecimientoDTO y se devuelve.
     * Si no se encuentra, se lanza una excepción RuntimeException.
     *
     * @param id El identificador único del acontecimiento que se desea buscar.
     * @return Un objeto AcontecimientoDTO que representa el acontecimiento encontrado.
     * @throws RuntimeException Si el acontecimiento no es encontrado.
     */
    public AcontecimientoDTO obtenerAcontecimientoPorId(Integer id) {
        // Busca el acontecimiento en el repositorio utilizando el ID proporcionado
        Acontecimiento acontecimiento = acontecimientoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Acontecimiento no encontrado"));
        // Crea un nuevo objeto AcontecimientoDTO y asigna los valores del acontecimiento encontrado
        AcontecimientoDTO dto = new AcontecimientoDTO();
        dto.setId(acontecimiento.getId());
        dto.setNombre(acontecimiento.getNombre());
        dto.setDescripcion(acontecimiento.getDescripcion());
        dto.setUbicacion(acontecimiento.getUbicacion());
        dto.setImg(acontecimiento.getImg());
        // Devuelve el objeto AcontecimientoDTO
        return dto;
    }


    /**
     * Este método se encarga de crear un nuevo acontecimiento en la base de datos.
     * Recibe un objeto AcontecimientoDTO que contiene la información necesaria para crear el acontecimiento.
     * Luego, verifica si el usuario autenticado tiene los permisos necesarios (ROL ONG o ADMIN).
     * Si tiene permisos, convierte el objeto DTO en una entidad Acontecimiento y lo guarda en la base de datos.
     *
     * @param acontecimientoDTO Un objeto AcontecimientoDTO que contiene los datos del nuevo acontecimiento.
     * @return Un objeto AcontecimientoDTO que representa el acontecimiento creado.
     * @throws SecurityException Si el usuario no tiene permisos para crear acontecimientos.
     */
    public AcontecimientoDTO crearNuevoAcontecimiento(AcontecimientoDTO acontecimientoDTO) {
        // Obtiene la autenticación del contexto de seguridad
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nombre = authentication.getName();
        Usuario usuario = usuarioService.buscarUsuarioPorNombre(nombre);

        // Verifica si el usuario tiene permisos para crear acontecimientos
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

    /**
     * Este método se encarga de eliminar un acontecimiento existente en la base de datos.
     * Verifica si el usuario autenticado tiene los permisos necesarios (ROL ONG o ADMIN).
     * Si tiene permisos, busca el acontecimiento por su ID y lo elimina de la base de datos.
     *
     * @param id El identificador único del acontecimiento que se desea eliminar.
     * @return Un mensaje indicando el resultado de la operación de eliminación.
     * @throws SecurityException Si el usuario no tiene permisos para eliminar acontecimientos.
     * @throws RuntimeException Si el acontecimiento no es encontrado.
     */
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
        // Obtiene la autenticación del contexto de seguridad
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nombre = authentication.getName();
        Usuario usuario = usuarioService.buscarUsuarioPorNombre(nombre);

        // Verifica si el usuario tiene permisos para eliminar acontecimientos
        if (usuario == null || !(usuario.getRol().equals(Rol.ADMIN) || usuario.getRol().equals(Rol.ONG))) {
            throw new SecurityException("No tienes permiso para editar acontecimientos");
        }

        // Busca el acontecimiento en el repositorio utilizando el ID proporcionado
        Acontecimiento acontecimiento = acontecimientoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Acontecimiento no encontrado"));

        acontecimiento.setNombre(acontecimientoDTO.getNombre());
        acontecimiento.setDescripcion(acontecimientoDTO.getDescripcion());
        acontecimiento.setUbicacion(acontecimientoDTO.getUbicacion());
        acontecimiento.setImg(acontecimientoDTO.getImg());

        // Devuelve un mensaje indicando que el acontecimiento ha sido eliminado correctamente
        return acontecimientoRepository.save(acontecimiento);
    }


}

