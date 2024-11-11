package com.valencia.proyecto1evaluacion.controladores;

import com.valencia.proyecto1evaluacion.dtos.PerfilProveedoresDTO;
import com.valencia.proyecto1evaluacion.modelos.Proveedores;
import com.valencia.proyecto1evaluacion.servicios.ProveedorService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/perfilProveedor")
@AllArgsConstructor
public class PerfilProveedorController {
    private ProveedorService proveedoresService;

    @GetMapping("/all")
    public List<PerfilProveedoresDTO>getAllPerfilesProveedores(){
        List<PerfilProveedoresDTO> proveedores = proveedoresService.getAll();
        return proveedores;
    }

    @GetMapping()
    public Proveedores getById(@RequestParam Integer id){
        Proveedores proveedor = proveedoresService.getById(id);
        return proveedor;
    }

    @PostMapping("/id/{id}")
    public Proveedores getByIdPath(@PathVariable Integer id){
        Proveedores proveedor = proveedoresService.getById(id);
        return proveedor;
    }

    @DeleteMapping()
    public String eliminar(@RequestParam Integer id){
        return proveedoresService.eliminar(id);
    }

    @GetMapping("/buscar")
    public List<PerfilProveedoresDTO> buscar(@RequestParam String busqueda){
        List<PerfilProveedoresDTO> dtos = proveedoresService.buscar(busqueda);
        return dtos;
    }
}
