package com.banco.servicio;

import com.banco.dto.ClienteDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IClienteServicio {
    ClienteDTO crearCliente(ClienteDTO clienteDTO);
    ClienteDTO obtenerCliente(Long id);
    List<ClienteDTO> obtenerClientes();
    Page<ClienteDTO> obtenerClientesPaginate(String search, int page, int size);
    ClienteDTO editarCliente(String id, ClienteDTO clienteDTO);
    ClienteDTO actualizarCliente(Long id, ClienteDTO clienteDTO);
    void eliminarCliente(Long id);
}
