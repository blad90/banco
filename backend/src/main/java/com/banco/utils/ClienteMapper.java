package com.banco.utils;

import com.banco.dto.ClienteDTO;
import com.banco.entidad.Cliente;

public class ClienteMapper {

    public static ClienteDTO mapToDTO(Cliente cliente) {
        return new ClienteDTO(
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
                );
    }

    public static Cliente mapToEntity(ClienteDTO clienteDTO){
        Cliente c = new Cliente();
        c.setClienteId(clienteDTO.getClienteId());
        c.setNombre(clienteDTO.getNombre());
        c.setGenero(clienteDTO.getGenero());
        c.setEdad(clienteDTO.getEdad());
        c.setIdentificacion(clienteDTO.getIdentificacion());
        c.setDireccion(clienteDTO.getDireccion());
        c.setTelefono(clienteDTO.getTelefono());
        c.setContrasena(clienteDTO.getContrasena());
        c.setEstado(clienteDTO.getEstado());
        return c;
    }
}
