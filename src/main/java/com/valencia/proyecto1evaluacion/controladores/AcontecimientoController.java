package com.valencia.proyecto1evaluacion.controladores;

import com.valencia.proyecto1evaluacion.dtos.AcontecimientoDTO;
import com.valencia.proyecto1evaluacion.dtos.ConsultaAcontecimientoDTO;
import com.valencia.proyecto1evaluacion.repositorio.AcontecimientoRepository;
import com.valencia.proyecto1evaluacion.dtos.AcontecimientoInfoDTO;
import com.valencia.proyecto1evaluacion.modelos.Acontecimiento;
import com.valencia.proyecto1evaluacion.modelos.OngAcontecimiento;
import com.valencia.proyecto1evaluacion.repositorio.AcontecimientoRepository;
import com.valencia.proyecto1evaluacion.servicios.AcontecimientoService;
import com.valencia.proyecto1evaluacion.servicios.OngAcontecimientoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/acontecimiento")
@AllArgsConstructor
public class AcontecimientoController {

    private final AcontecimientoService acontecimientoService;
    private final OngAcontecimientoService ongAcontecimientoService;

    @GetMapping("/listar")
    public List<AcontecimientoDTO> listarAcontecimientos(){
        return acontecimientoService.getAll();
    }

//    @GetMapping("/ong/{id}/acontecimientos")//id de la ong
//    public List<Acontecimiento> obtenerAcontecimientosPorOng(@PathVariable Integer id) {
//        return acontecimientoService.obtenerAcontecimientosPorOng(id);
//
//
//    }

    @GetMapping("/ong/{id}/acontecimientos")
    public List<AcontecimientoDTO> obtenerAcontecimientosPorOng2(@PathVariable Integer id) {
        return ongAcontecimientoService.obtenerAcontecimientosPorOng(id);
    }

    @PostMapping("/crear")
    public AcontecimientoDTO crearAcontecimiento(@RequestBody AcontecimientoDTO acontecimientoDTO){
        return acontecimientoService.crearAcontecimiento(acontecimientoDTO);
    }

    /**
     * Endpoint para obtener el total recaudado por cada acontecimiento.
     *
     * @return Lista de ConsultasAcontecimientoDTO con el nombre del acontecimiento y el total recaudado.
     */
    @GetMapping("/total")
    public List<ConsultaAcontecimientoDTO> llamadaAcontecimiento() {
        return acontecimientoService.findTotalRecaudadoPorAcontecimiento();
    }

    @GetMapping("/total-donaciones")
    public ResponseEntity<Double> getTotalDonaciones() {
        Double totalDonaciones = acontecimientoService.findTotalDonaciones();
        return ResponseEntity.ok(totalDonaciones);
    }
    @GetMapping("/info")
    public List<AcontecimientoInfoDTO> obtenerInfoAcontecimiento()  {
        return acontecimientoService.obtenerInfoAcontecimiento();
    }
    @GetMapping("/detalles/{id}")
    public ResponseEntity<AcontecimientoDTO> obtenerAcontecimientoPorId(@PathVariable Integer id) {
        AcontecimientoDTO dto = acontecimientoService.obtenerAcontecimientoPorId(id);
        return ResponseEntity.ok(dto);
    }


    @GetMapping("/getById/{id}")
    public AcontecimientoDTO getById(@PathVariable Integer id) {
        return acontecimientoService.getById(id);
    }
}
