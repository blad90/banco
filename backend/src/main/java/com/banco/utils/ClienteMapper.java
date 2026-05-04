package com.banco.utils;

import com.banco.dto.ClienteDTO;
import com.banco.entidad.Cliente;

public class ClienteMapper {

    public static ClienteDTO mapToDTO(Cliente cliente) {
        return ClienteDTO.builder()
                .id(cliente.getId())
                .clienteId(cliente.getClienteId())
                .nombre(cliente.getNombre())
                .genero(cliente.getGenero())
                .edad(cliente.getEdad())
                .identificacion(cliente.getIdentificacion())
                .direccion(cliente.getDireccion())
                .telefono(cliente.getTelefono())
                .contrasena(cliente.getContrasena())
                .estado(cliente.getEstado())
                .build();
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
        c.setEstado(clienteDTO.getEstado() != null ? clienteDTO.getEstado() : true);
        return c;
    }
}
