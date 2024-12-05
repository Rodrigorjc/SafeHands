package com.valencia.proyecto1evaluacion.servicios;


import com.valencia.proyecto1evaluacion.dtos.ClienteDTO;
import com.valencia.proyecto1evaluacion.dtos.ClientePerfilDTO;
import com.valencia.proyecto1evaluacion.dtos.ImgDTO;
import com.valencia.proyecto1evaluacion.modelos.Cliente;
import com.valencia.proyecto1evaluacion.modelos.Usuario;
import com.valencia.proyecto1evaluacion.repositorio.ClienteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final UsuarioService usuarioService;

    public ImgDTO getImgbyId  (Integer id){
        Cliente cliente = clienteRepository.findClienteByUsuarioId(id);
        if (cliente == null) {
            throw new RuntimeException("Cliente not found for user ID: " + id);
        }
        ImgDTO imgDTO = new ImgDTO();
        imgDTO.setImg(cliente.getFotoPerfil());
        return imgDTO;
    }


    /**
     * Recupera todos los clientes de la base de datos.
     * Este método interactúa con el `ClienteRepository` para obtener todas las entidades `Cliente`.
     * Devuelve una lista de todos los clientes disponibles en la base de datos.
     *
     * @return una lista de objetos `Cliente` que representan a todos los clientes en la base de datos
     */

    // Obtener todos los clientes
    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    /**
     * Busca un cliente por su ID.
     * Este método interactúa con el `ClienteRepository` para buscar un cliente específico
     * en la base de datos utilizando su ID.
     * Devuelve un `Optional<Cliente>` que contiene el cliente si se encuentra, o vacío si no.
     *
     * @param id el ID del cliente a buscar
     * @return un `Optional<Cliente>` que contiene el cliente encontrado o vacío si no se encuentra
     */
    public Optional<Cliente> findById(Integer id) {
        return clienteRepository.findById(id);
    }

    /**
     * Crea un nuevo cliente.
     * Este método guarda un nuevo cliente en la base de datos.
     * Devuelve el cliente creado.
     *
     * @param cliente el cliente a crear
     * @return el cliente creado
     */
    public Cliente createCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    /**
     * Actualiza un cliente existente.
     * Este método busca un cliente por su ID y actualiza sus detalles con la información proporcionada.
     * Si el cliente existe, se actualizan sus campos y se guarda en la base de datos.
     * Devuelve un `Optional<Cliente>` que contiene el cliente actualizado si se encuentra, o vacío si no.
     *
     * @param id el ID del cliente a actualizar
     * @param clienteDetails los detalles del cliente a actualizar
     * @return un `Optional<Cliente>` que contiene el cliente actualizado o vacío si no se encuentra
     */
    public Optional<Cliente> updateCliente(Integer id, Cliente clienteDetails) {
        return clienteRepository.findById(id).map(existingCliente -> {
            existingCliente.setDni(clienteDetails.getDni());
            existingCliente.setUsuario(clienteDetails.getUsuario());
            return clienteRepository.save(existingCliente);
        });
    }

    /**
     * Elimina un cliente por su ID.
     *
     * Este método interactúa con el `ClienteRepository` para eliminar un cliente específico
     * de la base de datos utilizando su ID.
     *
     * @param id el ID del cliente a eliminar
     */
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
}