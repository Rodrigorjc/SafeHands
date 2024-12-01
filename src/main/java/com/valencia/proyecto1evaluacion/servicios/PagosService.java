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

    public PagosDTO findTotalDonaciones() {
        Double totalDonaciones = pagosRepository.findTotalDonaciones();
        return new PagosDTO(totalDonaciones);
    }

    /**
     * Devuelve todos los pagos
     *
     * @return
     */
    public List<PagosDTO> getAll(){
        List<Pagos> pagos = pagosRepository.findAll();
        List<PagosDTO> pagosDTOS = new ArrayList<>();
        for (Pagos p : pagos ) {
            PagosDTO pagosDTO = new PagosDTO();
            pagosDTO.setId(p.getId());
            pagosDTO.setEstado(p.getEstado());
            pagosDTO.setCuantia(p.getCuantia());

        }
        return pagosDTOS;
    }

    /**
     * Busca un pago por id
     *
     * @param id
     * @return
     */
    public Pagos getById(Integer id){
        return pagosRepository.findById(id).orElse(null);
    }

    /**
     * Crea un nuevo pago
     *
     * @param pagosDTO
     * @return
     */
    public Pagos crearNuevo(PagosDTO pagosDTO){
        Pagos entity = new Pagos();
        entity.setCuantia(pagosDTO.getCuantia());
        entity.setEstado(pagosDTO.getEstado());
        entity.setId(pagosDTO.getId());


        return pagosRepository.save(entity);
    }

    /**
     * Edita un pago
     *
     * @param dto
     * @param id
     * @return
     */
    public Pagos editar(PagosDTO dto, Integer id){
        Pagos entity = pagosRepository.getReferenceById(id);
        entity.setCuantia(dto.getCuantia());
        entity.setEstado(dto.getEstado());
        entity.setId(dto.getId());

        return pagosRepository.save(entity);
    }

    /**
     * Guarda un pago
     *
     * @param dto
     * @return
     */
    public Pagos guardar(PagosDTO dto){
        Pagos entity = new Pagos();
        entity.setCuantia(dto.getCuantia());
        entity.setEstado(dto.getEstado());
        entity.setId(dto.getId());

        return pagosRepository.save(entity);
    }

    /**
     * Elimina un pago
     *
     * @param id
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


    public PagosDTO crearPago(PagosDTO pagosDTO) {
        Pagos pago = new Pagos();
        pago.setId(pagosDTO.getId());
        pago.setCuantia(pagosDTO.getCuantia());
        pago.setEstado(pagosDTO.getEstado());

        return pagosDTO;
    }


    public void crearPagoPedido(Double cuantia, Pedido pedido) {
        Pagos pago = new Pagos();
        pago.setCuantia(cuantia.floatValue());
        pago.setPedido(pedido);
        pago.setEstado(true);
        pagosRepository.save(pago);
    }

}
