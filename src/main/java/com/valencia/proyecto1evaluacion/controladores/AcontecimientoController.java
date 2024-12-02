package com.valencia.proyecto1evaluacion.controladores;

import com.valencia.proyecto1evaluacion.dtos.AcontecimientoDTO;
import com.valencia.proyecto1evaluacion.dtos.AcontecimientoInfoDTO;
import com.valencia.proyecto1evaluacion.dtos.ProveedoresDTO;
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

    @GetMapping("/info")
    public List<AcontecimientoInfoDTO> obtenerInfoAcontecimiento()  {
        return acontecimientoService.obtenerInfoAcontecimiento();
    }
    @GetMapping("/detalles/{id}")
    public ResponseEntity<AcontecimientoDTO> obtenerAcontecimientoPorId(@PathVariable Integer id) {
        AcontecimientoDTO dto = acontecimientoService.obtenerAcontecimientoPorId(id);
        return ResponseEntity.ok(dto);
    }
    @PostMapping("/crear/acontecimiento/admin/ong")
    public AcontecimientoDTO crearAcontecimientoAdminOng(@RequestBody AcontecimientoDTO acontecimientoDTO){
        return acontecimientoService.crearNuevoAcontecimiento(acontecimientoDTO);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarAcontecimiento(@PathVariable Integer id) {
        String mensaje = acontecimientoService.eliminarAcontecimiento(id);
        return ResponseEntity.ok(mensaje);
    }
    @PutMapping("/editar/{id}")
    public Acontecimiento editarAcontecimiento(@PathVariable Integer id, @RequestBody AcontecimientoDTO acontecimientoDTO) {
        return acontecimientoService.editarAcontecimiento(id, acontecimientoDTO);
    }



}
