package com.valencia.proyecto1evaluacion.controladores;


import com.valencia.proyecto1evaluacion.dtos.AcontecimientoDTO;
import com.valencia.proyecto1evaluacion.dtos.PedidoDTO;
import com.valencia.proyecto1evaluacion.servicios.PedidosService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidosService pedidosService;

    @PostMapping("/realizar/pedido")
    public ResponseEntity<String> realizarPedido(@RequestBody PedidoDTO pedido) {
        try {
            pedidosService.realizarPedido(pedido);
            return ResponseEntity.ok("Pedido realizado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al realizar el pedido");
        }
    }

    @PostMapping("/crear")
    public PedidoDTO crearPedido(@RequestBody PedidoDTO pedidoDTO){
        return pedidosService.crearPedido(pedidoDTO);
    }
}
