package com.valencia.proyecto1evaluacion.servicios;


import com.valencia.proyecto1evaluacion.dtos.ClientePerfilDTO;
import com.valencia.proyecto1evaluacion.dtos.ImgDTO;
import com.valencia.proyecto1evaluacion.dtos.NombreImgDTO;
import com.valencia.proyecto1evaluacion.modelos.Cliente;
import com.valencia.proyecto1evaluacion.modelos.Usuario;
import com.valencia.proyecto1evaluacion.repositorio.ClienteRepository;
import com.valencia.proyecto1evaluacion.repositorio.LineaPedidoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final UsuarioService usuarioService;
    private final LineaPedidoRepository lineaPedidoRepository;

    public ImgDTO getImgbyId  (Integer id){
        Cliente cliente = clienteRepository.findClienteByUsuarioId(id);
        if (cliente == null) {
            throw new RuntimeException("Cliente not found for user ID: " + id);
        }
        ImgDTO imgDTO = new ImgDTO();
        imgDTO.setImg(cliente.getFotoPerfil());
        return imgDTO;
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

    public ClientePerfilDTO getCliente(Integer id) {
        ClientePerfilDTO clientePerfilDTO = new ClientePerfilDTO();
        Cliente cliente = clienteRepository.findClienteByUsuarioId(id);
        Usuario usuario = usuarioService.getById(id);
        clientePerfilDTO.setEmail(usuario.getEmail());
        clientePerfilDTO.setUsername(usuario.getUsername());
        clientePerfilDTO.setImg(cliente.getFotoPerfil());
        clientePerfilDTO.setDni(cliente.getDni());
        return clientePerfilDTO;
    }

    public Optional<Cliente> updateClientePerfil(Integer id, ClientePerfilDTO clientePerfilDTO) {
        Cliente cliente = clienteRepository.findClienteByUsuarioId(id);
        if (cliente != null) {
            cliente.setFotoPerfil(clientePerfilDTO.getImg());
            cliente.setDni(clientePerfilDTO.getDni());

            // Update the email in the Usuario entity
            Usuario usuario = cliente.getUsuario();
            usuario.setEmail(clientePerfilDTO.getEmail());
            usuario.setUsername(clientePerfilDTO.getUsername());
            usuarioService.updateUsuario(usuario);

            clienteRepository.save(cliente);
            return Optional.of(cliente);
        }
        return Optional.empty();
    }

    public List<NombreImgDTO> getProductosDonados(Integer userId) {
        List<Object[]> results = lineaPedidoRepository.findDonatedProductsByUserId(userId);
        List<NombreImgDTO> productos = new ArrayList<>();

        for (Object[] result : results) {
            String nombre = (String) result[0];
            String img = (String) result[1];
            productos.add(new NombreImgDTO(nombre, img));
        }

        return productos;
    }

    public List<NombreImgDTO> getAconteciminetosDonados(Integer userId) {
        List<Object[]> results = lineaPedidoRepository.findDonatedEventsByUserId(userId);
        List<NombreImgDTO> acontecimientos = new ArrayList<>();

        for (Object[] result : results) {
            String nombre = (String) result[0];
            String img = (String) result[1];
            acontecimientos.add(new NombreImgDTO(nombre, img));
        }

        return acontecimientos;
    }
}