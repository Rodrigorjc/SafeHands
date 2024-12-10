package com.valencia.proyecto1evaluacion.servicios;

import com.valencia.proyecto1evaluacion.dtos.AcontecimientoCrearDTO;
import com.valencia.proyecto1evaluacion.dtos.AcontecimientoDTO;
import com.valencia.proyecto1evaluacion.dtos.PagosDTO;
import com.valencia.proyecto1evaluacion.modelos.Acontecimiento;
import com.valencia.proyecto1evaluacion.modelos.Pagos;
import com.valencia.proyecto1evaluacion.modelos.Pedido;
import com.valencia.proyecto1evaluacion.repositorio.PagosRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
@AllArgsConstructor
public class PagosService {

    private PagosRepository pagosRepository;

    /**
     * Este método se encarga de obtener el total de donaciones realizadas.
     * Consulta el repositorio de pagos para obtener la suma total de todas las donaciones.
     * La información obtenida se encapsula en un objeto PagosDTO.
     *
     * @return Un objeto PagosDTO que contiene el total de donaciones realizadas.
     *         El total se obtiene sumando todas las cantidades donadas registradas en los pagos.
     */
    public PagosDTO findTotalDonaciones() {
        // Obtiene el total de donaciones desde el repositorio de pagos
        Double totalDonaciones = pagosRepository.findTotalDonaciones();
        // Crea un nuevo objeto PagosDTO con el total de donaciones
        return new PagosDTO(totalDonaciones);
    }

    /**
     * Este método se encarga de obtener todos los pagos registrados en el sistema.
     * Consulta el repositorio de pagos para obtener una lista de todos los pagos.
     * Cada pago se mapea a un objeto PagosDTO, que contiene la información del pago.
     *
     * @return Una lista de objetos PagosDTO que representan todos los pagos registrados.
     *         Cada objeto en la lista contiene el ID del pago, su estado y la cuantía.
     */
    public List<PagosDTO> getAll(){
        // Obtiene todos los pagos desde el repositorio de pagos
        List<Pagos> pagos = pagosRepository.findAll();

        // Crea una lista para almacenar los objetos PagosDTO
        List<PagosDTO> pagosDTOS = new ArrayList<>();

        // Itera sobre la lista de pagos y mapea cada pago a un objeto PagosDTO
        for (Pagos p : pagos ) {
            PagosDTO pagosDTO = new PagosDTO();
            pagosDTO.setId(p.getId());
            pagosDTO.setEstado(p.getEstado());
            pagosDTO.setCuantia(p.getCuantia());

        }
        // Añade el objeto PagosDTO a la lista de pagosDTOs
        return pagosDTOS;
    }

    /**
     * Este método se encarga de buscar un pago por su ID.
     * Consulta el repositorio de pagos para obtener un pago específico basado en su ID.
     * Si el pago no se encuentra, devuelve null.
     *
     * @param id El ID del pago que se desea buscar.
     * @return Un objeto Pagos que representa el pago encontrado, o null si no se encuentra el pago.
     */
    public Pagos getById(Integer id){
        // Busca el pago en el repositorio de pagos por su ID
        return pagosRepository.findById(id).orElse(null);
    }

    /**
     * Este método se encarga de crear un nuevo pago en el sistema.
     * Recibe un objeto PagosDTO que contiene la información del pago a crear.
     * La información del pago se mapea a una entidad Pagos y se guarda en el repositorio de pagos.
     *
     * @param pagosDTO Un objeto PagosDTO que contiene la información del pago a crear.
     * @return Un objeto Pagos que representa el pago creado y guardado en el repositorio.
     */
    public Pagos crearNuevo(PagosDTO pagosDTO){
        // Crea una nueva entidad Pagos
        Pagos entity = new Pagos();

        entity.setCuantia(pagosDTO.getCuantia());
        entity.setEstado(pagosDTO.getEstado());
        entity.setId(pagosDTO.getId());

        // Guarda la entidad Pagos en el repositorio y la devuelve
        return pagosRepository.save(entity);
    }

    /**
     * Este método se encarga de editar un pago existente en el sistema.
     * Recibe un objeto PagosDTO que contiene la nueva información del pago y el ID del pago a editar.
     * La información del pago se actualiza en la entidad Pagos correspondiente y se guarda en el repositorio de pagos.
     *
     * @param dto Un objeto PagosDTO que contiene la nueva información del pago.
     * @param id El ID del pago que se desea editar.
     * @return Un objeto Pagos que representa el pago editado y guardado en el repositorio.
     */
    public Pagos editar(PagosDTO dto, Integer id){
        Pagos entity = pagosRepository.getReferenceById(id);

        entity.setCuantia(dto.getCuantia());
        entity.setEstado(dto.getEstado());
        entity.setId(dto.getId());

        return pagosRepository.save(entity);
    }

    /**
     * Este método se encarga de guardar un pago en el sistema.
     * Recibe un objeto PagosDTO que contiene la información del pago a guardar.
     * La información del pago se mapea a una entidad Pagos y se guarda en el repositorio de pagos.
     *
     * @param dto Un objeto PagosDTO que contiene la información del pago a guardar.
     * @return Un objeto Pagos que representa el pago guardado en el repositorio.
     */
    public Pagos guardar(PagosDTO dto){
        Pagos entity = new Pagos();
        entity.setCuantia(dto.getCuantia());
        entity.setEstado(dto.getEstado());
        entity.setId(dto.getId());

        return pagosRepository.save(entity);
    }

    /**
     * Este método se encarga de eliminar un pago del sistema.
     * Recibe el ID del pago que se desea eliminar.
     * Si el pago no se encuentra, devuelve un mensaje indicando que el pago no existe.
     * Si el pago se elimina correctamente, devuelve un mensaje de éxito.
     * Si ocurre un error durante la eliminación, devuelve un mensaje de error.
     *
     * @param id El ID del pago que se desea eliminar.
     * @return Un mensaje indicando el resultado de la operación de eliminación.
     */
    public String eliminar(Integer id){
        String mensaje;
        Pagos pago  = getById(id);

        if(pago == null){
            mensaje = "El pago con el id que está buscando no existe.";
        }
        try{
            pagosRepository.deleteById(id);

            pago = getById(id);
            if (pago != null) {
                mensaje = "No se ha podido eliminar el pago.";
            }else {
                mensaje = "Pago eliminado correctamente.";
            }
        }catch (Exception e){
            mensaje = "No se ha podido eliminar el pago.";
        }
        return mensaje;
    }


    /**
     * Este método se encarga de crear un nuevo pago en el sistema.
     * Recibe un objeto PagosDTO que contiene la información del pago a crear.
     * La información del pago se mapea a una entidad Pagos y se guarda en el repositorio de pagos.
     *
     * @param pagosDTO Un objeto PagosDTO que contiene la información del pago a crear.
     * @return Un objeto PagosDTO que representa el pago creado.
     */
    public PagosDTO crearPago(PagosDTO pagosDTO) {
        Pagos pago = new Pagos();
        pago.setId(pagosDTO.getId());
        pago.setCuantia(pagosDTO.getCuantia());
        pago.setEstado(pagosDTO.getEstado());

        return pagosDTO;
    }



    /**
     * Este método se encarga de crear un nuevo pago asociado a un pedido en el sistema.
     * Recibe la cuantía del pago y el pedido al que está asociado.
     * La información del pago se mapea a una entidad Pagos y se guarda en el repositorio de pagos.
     *
     * @param cuantia La cuantía del pago a crear.
     * @param pedido El pedido al que está asociado el pago.
     */
    public void crearPagoPedido(Double cuantia, Pedido pedido) {
        Pagos pago = new Pagos();
        pago.setCuantia(cuantia.floatValue());
        pago.setPedido(pedido);
        pago.setEstado(true);
        pagosRepository.save(pago);
    }

}
