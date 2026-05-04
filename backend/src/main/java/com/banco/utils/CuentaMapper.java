package com.banco.utils;

import com.banco.dto.CuentaDTO;
import com.banco.entidad.Cliente;
import com.banco.entidad.Cuenta;

public class CuentaMapper {
    public static CuentaDTO toDTO(Cuenta cuenta) {
        return CuentaDTO.builder()
                .id(cuenta.getId())
                .numeroCuenta(cuenta.getNumeroCuenta())
                .tipoCuenta(cuenta.getTipoCuenta())
                .saldoInicial(cuenta.getSaldoInicial())
                .saldoDisponible(cuenta.getSaldoDisponible())
                .estado(cuenta.getEstado())
                .clienteId(cuenta.getCliente().getId())
                .clienteNombre(cuenta.getCliente().getNombre())
                .build();
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
