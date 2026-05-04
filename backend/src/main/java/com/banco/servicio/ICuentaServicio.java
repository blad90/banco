package com.banco.servicio;

import com.banco.dto.CuentaDTO;

import java.util.List;

public interface ICuentaServicio {
    CuentaDTO crearCuenta(CuentaDTO cuentaDTO);
    CuentaDTO actualizarCuenta(Long id, CuentaDTO cuentaDTO);
    void eliminarCuenta(Long id);
    List<CuentaDTO> obtenerTodas();
    CuentaDTO obtenerCuenta(Long id);
    List<CuentaDTO> obtenerCuentaPorCliente(Long clienteId);
}
