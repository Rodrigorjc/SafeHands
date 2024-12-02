package com.valencia.proyecto1evaluacion.controladores;

import com.valencia.proyecto1evaluacion.dtos.*;
import com.valencia.proyecto1evaluacion.modelos.Ong;
import com.valencia.proyecto1evaluacion.servicios.AdminService;
import com.valencia.proyecto1evaluacion.servicios.ProveedorService;
import com.valencia.proyecto1evaluacion.servicios.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;
    private final ProveedorService proveedorService;


    @PostMapping("/register")
    public AuthenticationDTO registerAdmin(@RequestBody UsuarioDto userDTO) {
        return adminService.registerAdmin(userDTO);
    }

    @DeleteMapping("/eliminar/ong/{ongId}")
    public void eliminarOng(@PathVariable Integer ongId) {
        adminService.eliminarOng(ongId);
    }


    @PutMapping("/editar/ong/{ongId}")
    public OngDTO editarOng(@PathVariable Integer ongId, @RequestBody OngDTO updatedOng) {
        return adminService.editarOng(ongId, updatedOng);

    }

    @PutMapping("/editar/proveedor/{id}")
    public ProveedoresDTO editarProveedor(@PathVariable Integer id, @RequestBody ProveedoresDTO proveedorDto) {
        return adminService.editarProveedor(id, proveedorDto);
    }

    @DeleteMapping("/eliminar/proveedor/{id}")
    public void eliminarProveedor(@PathVariable Integer id) {
        adminService.eliminarProveedor(id);
    }


    @PostMapping("crear/proveedor")
    public AuthenticationDTO crearProveedor(@RequestBody CrearProveedorDTO proveedorDto) {
        return proveedorService.registrarProveedorAdmin(proveedorDto);
    }
}
