package com.banco.servicio;

import com.banco.dto.ClienteDTO;
import com.banco.entidad.Cliente;
import com.banco.excepciones.BusinessException;
import com.banco.excepciones.ResourceNotFoundException;
import com.banco.repositorio.IClienteRepositorio;
import com.banco.utils.ClienteMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public ClienteDTO crearCliente(ClienteDTO clienteDTO) {
        if (clienteRepositorio.existsByClienteId(clienteDTO.getClienteId())) {
            throw new BusinessException("Ya existe un cliente con el clienteId: " + clienteDTO.getClienteId());
        }
        if (clienteRepositorio.existsByIdentificacion(clienteDTO.getIdentificacion())) {
            throw new BusinessException("Ya existe un cliente con la identificación: " + clienteDTO.getIdentificacion());
        }
        Cliente cliente = ClienteMapper.mapToEntity(clienteDTO);
        return ClienteMapper.mapToDTO(clienteRepositorio.save(cliente));
    }

    @Override
    public ClienteDTO obtenerCliente(Long id) {
        return ClienteMapper.mapToDTO(clienteRepositorio.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con id: " + id)));
    }

    @Override
    public List<ClienteDTO> obtenerClientes() {
        return clienteRepositorio.findAll()
                .stream()
                .map(cliente -> new ClienteDTO(
                        cliente.getId(),
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
    public Page<ClienteDTO> obtenerClientesPaginate(String search, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return clienteRepositorio.findByClienteNombreContainingIgnoreCase(search, pageable)
                .map(ClienteMapper::mapToDTO);
    }

    @Override
    public ClienteDTO editarCliente(String clienteId, ClienteDTO clienteDTO) {
        Cliente clienteEditado = clienteRepositorio.findClienteByClienteId(clienteId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Cliente no encontrado: " + clienteId));

        clienteEditado.setNombre(clienteDTO.getNombre());
        clienteEditado.setGenero(clienteDTO.getGenero());
        clienteEditado.setEdad(clienteDTO.getEdad());
        clienteEditado.setIdentificacion(clienteDTO.getIdentificacion());
        clienteEditado.setDireccion(clienteDTO.getDireccion());
        clienteEditado.setTelefono(clienteDTO.getTelefono());
        clienteEditado.setContrasena(clienteDTO.getContrasena());
        clienteEditado.setEstado(clienteDTO.getEstado());

        Cliente guardado = clienteRepositorio.save(clienteEditado);

        return ClienteMapper.mapToDTO(guardado);
    }

    @Override
    public ClienteDTO actualizarCliente(Long id, ClienteDTO clienteDTO) {
        Cliente clienteEditado = clienteRepositorio.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con id: " + id));
        // Check uniqueness ignoring self
        clienteRepositorio.findClienteByClienteId(clienteDTO.getClienteId()).ifPresent(c -> {
            if (!c.getId().equals(id)) throw new BusinessException("clienteId ya existe!");
        });
        clienteEditado.setClienteId(clienteDTO.getClienteId());
        clienteEditado.setNombre(clienteDTO.getNombre());
        clienteEditado.setGenero(clienteDTO.getGenero());
        clienteEditado.setEdad(clienteDTO.getEdad());
        clienteEditado.setIdentificacion(clienteDTO.getIdentificacion());
        clienteEditado.setDireccion(clienteDTO.getDireccion());
        clienteEditado.setTelefono(clienteDTO.getTelefono());
        clienteEditado.setContrasena(clienteDTO.getContrasena());
        clienteEditado.setEstado(clienteDTO.getEstado());
        return ClienteMapper.mapToDTO(clienteRepositorio.save(clienteEditado));
    }

    @Override
    public void eliminarCliente(Long id) {
        clienteRepositorio.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con id: " + id));
        clienteRepositorio.deleteById(id);
    }
}
