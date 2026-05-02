package com.banco.servicio;

import com.banco.dto.ClienteDTO;
import com.banco.entidad.Cliente;
import com.banco.repositorio.IClienteRepositorio;
import com.banco.utils.ClienteMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteServicio implements IClienteServicio{
    private final IClienteRepositorio clienteRepositorio;

    public ClienteServicio(IClienteRepositorio clienteRepositorio) {
        this.clienteRepositorio = clienteRepositorio;
    }

    @Override
    public String crearCliente(ClienteDTO clienteDTO) {
        Cliente nuevoCliente = new Cliente();
        nuevoCliente.setNombre(clienteDTO.getNombre());
        nuevoCliente.setGenero(clienteDTO.getGenero());
        nuevoCliente.setEdad(clienteDTO.getEdad());
        nuevoCliente.setIdentificacion(clienteDTO.getIdentificacion());
        nuevoCliente.setDireccion(clienteDTO.getDireccion());
        nuevoCliente.setTelefono(clienteDTO.getTelefono());
        nuevoCliente.setClienteId(clienteDTO.getClienteId());
        nuevoCliente.setContrasena(clienteDTO.getContrasena());
        nuevoCliente.setEstado(clienteDTO.getEstado());

        clienteRepositorio.save(nuevoCliente);

        return "Cliente registrado exitosamente!";
    }

    @Override
    public ClienteDTO obtenerCliente(Long id) {
        return clienteRepositorio.findById(id)
                .map(ClienteMapper::mapToDTO).get();
    }

    @Override
    public List<ClienteDTO> obtenerClientes() {
        return clienteRepositorio.findAll()
                .stream()
                .map(cliente -> new ClienteDTO(
                        cliente.getNombre(),
                        cliente.getGenero(),
                        cliente.getEdad(),
                        cliente.getIdentificacion(),
                        cliente.getDireccion(),
                        cliente.getTelefono(),
                        cliente.getClienteId(),
                        cliente.getContrasena(),
                        cliente.getEstado()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public String editarCliente(Long id, ClienteDTO clienteDTO) {
        Cliente clienteEditado = new Cliente();
        clienteEditado.setNombre(clienteDTO.getNombre());
        clienteEditado.setGenero(clienteDTO.getGenero());
        clienteEditado.setEdad(clienteDTO.getEdad());
        clienteEditado.setIdentificacion(clienteDTO.getIdentificacion());
        clienteEditado.setDireccion(clienteDTO.getDireccion());
        clienteEditado.setTelefono(clienteDTO.getTelefono());
        clienteEditado.setClienteId(clienteDTO.getClienteId());
        clienteEditado.setContrasena(clienteDTO.getContrasena());
        clienteEditado.setEstado(clienteDTO.getEstado());

        clienteRepositorio.save(clienteEditado);

        return "Cliente editado exitosamente!";
    }

    @Override
    public String actualizarCliente(Long id, ClienteDTO clienteDTO) {
        Cliente clienteActualizado = new Cliente();
        clienteActualizado.setNombre(clienteDTO.getNombre());
        clienteActualizado.setGenero(clienteDTO.getGenero());
        clienteActualizado.setEdad(clienteDTO.getEdad());
        clienteActualizado.setIdentificacion(clienteDTO.getIdentificacion());
        clienteActualizado.setDireccion(clienteDTO.getDireccion());
        clienteActualizado.setTelefono(clienteDTO.getTelefono());
        clienteActualizado.setClienteId(clienteDTO.getClienteId());
        clienteActualizado.setContrasena(clienteDTO.getContrasena());
        clienteActualizado.setEstado(clienteDTO.getEstado());

        clienteRepositorio.save(clienteActualizado);

        return "Cliente actualizado exitosamente!";
    }

    @Override
    public String eliminarCliente(Long id) {
        clienteRepositorio.deleteById(id);
        return "Cliente de ID: #" + id + " eliminado";
    }
}
