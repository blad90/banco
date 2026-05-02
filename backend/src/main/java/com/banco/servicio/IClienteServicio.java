package com.banco.servicio;

import com.banco.dto.ClienteDTO;

import java.util.List;

public interface IClienteServicio {
    String crearCliente(ClienteDTO clienteDTO);
    ClienteDTO obtenerCliente(Long id);
    List<ClienteDTO> obtenerClientes();
    String editarCliente(Long id, ClienteDTO clienteDTO);
    String actualizarCliente(Long id, ClienteDTO clienteDTO);
    String eliminarCliente(Long id);
}
