package com.valencia.proyecto1evaluacion.controladores;


import com.valencia.proyecto1evaluacion.dtos.AuthenticationDTO;
import com.valencia.proyecto1evaluacion.dtos.CrearProveedorDTO;
import com.valencia.proyecto1evaluacion.dtos.ImgDTO;
import com.valencia.proyecto1evaluacion.dtos.ProveedoresDTO;
import com.valencia.proyecto1evaluacion.modelos.Proveedores;
import com.valencia.proyecto1evaluacion.servicios.ProveedorService;
import lombok.AllArgsConstructor;
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
        return proveedorService.listarProveedores();
    }

    //listar proveedores por id, cambiar id por usuario
    @GetMapping("/obtenerId/{idProveedor}")
    public ProveedoresDTO obtenerIdProveedor(@PathVariable Integer idProveedor) {
        return proveedorService.obtenerProveedorPorId(idProveedor);
    }}
