package com.valencia.proyecto1evaluacion.controladores;

import com.valencia.proyecto1evaluacion.dtos.AuthenticationDTO;
import com.valencia.proyecto1evaluacion.dtos.UsuarioDto;
import com.valencia.proyecto1evaluacion.modelos.Ong;
import com.valencia.proyecto1evaluacion.servicios.AdminService;
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


    @PostMapping("/register")
    public AuthenticationDTO registerAdmin(@RequestBody UsuarioDto userDTO) {
        return adminService.registerAdmin(userDTO);
    }

    @DeleteMapping("/eliminar/ong/{ongId}")
    public void eliminarOng(@PathVariable Integer ongId) {
        adminService.eliminarOng(ongId);
    }
    @PutMapping("/editar/{ongId}")
    public Ong editarOng(@PathVariable Integer ongId, @RequestBody Ong updatedOng) {
        return adminService.editarOng(ongId, updatedOng);

    }

}
