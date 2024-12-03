package com.valencia.proyecto1evaluacion.controladores;


import com.valencia.proyecto1evaluacion.dtos.*;
import com.valencia.proyecto1evaluacion.servicios.ProveedorService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/proveedor")
@AllArgsConstructor
public class ProveedorController {


    ProveedorService proveedorService;

//    @PostMapping("/crear")
//    public Proveedores crearProveedor(@RequestBody ProveedoresDTO proveedor){
//        return proveedorService.crearProveedor(proveedor);
//    }


    @PostMapping("/registrar")
    public AuthenticationDTO registrarProveedor(@RequestBody CrearProveedorDTO crearProveedorDTO) {
        return proveedorService.registrarProveedor(crearProveedorDTO);
    }


    @GetMapping("/img/{id}")
    public ImgDTO getItemById(@PathVariable("id") String id) {
        try {
            Integer intId = Integer.parseInt(id);
            return proveedorService.getImgbyId(intId);
        } catch (NumberFormatException e) {
            // Handle the error appropriately
            return proveedorService.getImgbyId(10);
        }
    }
    @GetMapping("/listar")
    public List<ProveedoresDTO> listarProveedoresDTO(){
        return proveedorService.listarProveedoresPorValidadoFalso();
    }

    //listar proveedores por id, cambiar id por usuario
    @GetMapping("/detalles/{idProveedor}")
    public ProveedoresDTO obtenerIdProveedor(@PathVariable Integer idProveedor) {
        return proveedorService.obtenerProveedorPorId(idProveedor);
    }
    @GetMapping("/listado")
    public List<ProveedoresSliderDTO> listadoProveedores(){
        return proveedorService.listadoProveedoresSlider();
    }

    @GetMapping("/listarSelect")

    public List<ProveedoresDTO> listarProveedoresSelect(){
        return proveedorService.listarProveedoresSelect();
    }

    @GetMapping("/ranking")
    public List<ProveedorRankingDTO> obtenerRankingProveedores() {
        return proveedorService.obtenerRankingProveedores();
    }


    @GetMapping("/info/proveedores")
    public List<ProveedorInfoDTO> obtenerInfoProveedores() {
        return proveedorService.obtenerInfoProveedores();
    }

    @GetMapping("/id/proveedor/{id}")
    public Integer getIdProveedorPorUsuarioId(@PathVariable Integer id) {
        return proveedorService.getIdProveedorPorUsuarioId(id);
    }


    @GetMapping("/usuario/{usuarioId}/proveedorId")
    public ResponseEntity<Integer> obtenerOngIdPorUsuarioId(@PathVariable Integer usuarioId) {
        Integer proveedorId = proveedorService.obtenerProveedorIdPorUsuarioId(usuarioId);
        return ResponseEntity.ok(proveedorId);
    }


}


