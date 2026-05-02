package com.banco.utils;

import com.banco.dto.ClienteDTO;
import com.banco.entidad.Cliente;

public class ClienteMapper {

    public static ClienteDTO mapToDTO(Cliente cliente) {
        return new ClienteDTO(
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
        return new Cliente(
                clienteDTO.getId(),
                clienteDTO.getNombre(),
                clienteDTO.getGenero(),
                clienteDTO.getEdad(),
                clienteDTO.getIdentificacion(),
                clienteDTO.getDireccion(),
                clienteDTO.getTelefono(),
                clienteDTO.getClienteId(),
                clienteDTO.getContrasena(),
                clienteDTO.getEstado()
        );
    }
}
