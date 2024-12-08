package com.valencia.proyecto1evaluacion.servicios;

import com.valencia.proyecto1evaluacion.dtos.PedidoDTO;
import com.valencia.proyecto1evaluacion.dtos.ProductoCarritoDTO;
import com.valencia.proyecto1evaluacion.modelos.LineaPedido;
import com.valencia.proyecto1evaluacion.modelos.Pedido;
import com.valencia.proyecto1evaluacion.repositorio.ClienteRepository;
import com.valencia.proyecto1evaluacion.repositorio.LineaPedidoRepository;
import com.valencia.proyecto1evaluacion.repositorio.PedidoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class PedidosService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final LineaPedidoRepository lineaPedidoRepository;
    private final PagosService pagosService;
    private final ProductoService productoService;
    private final AcontecimientoService acontecimientoService;

    public void realizarPedido(PedidoDTO pedidoDTO) {
        Pedido pedido = new Pedido();
        Double total = 0.0;
        pedido.setCliente(clienteRepository.findClienteByUsuarioId(pedidoDTO.getIdUsuario()));
        pedido.setFecha(LocalDateTime.now());
        pedidoRepository.save(pedido);
        for (ProductoCarritoDTO p: pedidoDTO.getProductos()) {
            LineaPedido lineaPedido = new LineaPedido();
            lineaPedido.setPedido(pedido);
            lineaPedido.setCantidad(p.getCantidad());
            lineaPedido.setProducto(productoService.getProductoById(p.getIdProducto()));
            lineaPedido.setPrecioUnitario(p.getPrecioUnitario().floatValue());
            lineaPedido.setAcontecimiento(acontecimientoService.getAconteciminetoById(p.getIdAcontecimiento()));
            lineaPedidoRepository.save(lineaPedido);
            total += p.getTotal();
        }
        pagosService.crearPagoPedido(total, pedido);
    }

}
