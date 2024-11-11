package com.valencia.proyecto1evaluacion.servicios;


import com.valencia.proyecto1evaluacion.modelos.Cliente;
import com.valencia.proyecto1evaluacion.repositorio.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    // Obtener todos los clientes
    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    // Obtener un cliente por su ID
    public Optional<Cliente> findById(Integer id) {
        return clienteRepository.findById(id);
    }

    // Crear un nuevo cliente
    public Cliente createCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    // Actualizar un cliente existente
    public Optional<Cliente> updateCliente(Integer id, Cliente clienteDetails) {
        return clienteRepository.findById(id).map(existingCliente -> {
            existingCliente.setDni(clienteDetails.getDni());
            existingCliente.setUsuario(clienteDetails.getUsuario());
            return clienteRepository.save(existingCliente);
        });
    }

    // Eliminar un cliente por su ID
    public void deleteCliente(Integer id) {
        clienteRepository.deleteById(id);
    }
}