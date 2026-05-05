package com.banco.utils;

import com.banco.dto.CuentaDTO;
import com.banco.entidad.Cliente;
import com.banco.entidad.Cuenta;

public class CuentaMapper {
    public static CuentaDTO toDTO(Cuenta cuenta) {
        return new CuentaDTO(
                cuenta.getId(),
                cuenta.getNumeroCuenta(),
                cuenta.getTipoCuenta(),
                cuenta.getSaldoInicial(),
                cuenta.getSaldoDisponible(),
                cuenta.getEstado(),
                cuenta.getCliente().getId(),
                cuenta.getCliente().getNombre());
    }

    public static Cuenta toEntity(CuentaDTO dto, Cliente cliente) {
        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta(dto.getNumeroCuenta());
        cuenta.setTipoCuenta(dto.getTipoCuenta());
        cuenta.setSaldoInicial(dto.getSaldoInicial());
        cuenta.setSaldoDisponible(dto.getSaldoInicial());
        cuenta.setEstado(dto.getEstado() != null ? dto.getEstado() : true);
        cuenta.setCliente(cliente);
        return cuenta;
    }
}
