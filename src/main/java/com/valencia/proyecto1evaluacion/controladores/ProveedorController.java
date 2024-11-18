package com.valencia.proyecto1evaluacion.controladores;


import com.valencia.proyecto1evaluacion.dtos.ProveedoresDTO;
import com.valencia.proyecto1evaluacion.modelos.Proveedores;
import com.valencia.proyecto1evaluacion.servicios.ProveedorService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/proveedor")
@AllArgsConstructor
public class ProveedorController {


    ProveedorService proveedorService;

    @PostMapping("/crear")
    public Proveedores crearProveedor(@RequestBody ProveedoresDTO proveedor){
        return proveedorService.crearProveedor(proveedor);
    }


    @PostMapping("/registrar")
    public Proveedores registrarProveedor(@RequestBody ProveedoresDTO proveedor) {
        return proveedorService.registrarProveedor(proveedor);
    }

    @GetMapping("/listar")
    public Iterable<Proveedores> listarProveedores(){
        return proveedorService.listarProveedores();
    }

}
