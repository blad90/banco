package com.banco.servicio;

import com.banco.dto.MovimientoDTO;

import java.util.List;

public interface IMovimientoServicio {
    List<MovimientoDTO> obtenerMovimientos();
    MovimientoDTO obtenerMovimiento(Long id);
    List<MovimientoDTO> obtenerMovimientoPorCuenta(Long cuentaId);
    MovimientoDTO crearMovimiento(MovimientoDTO movimientoDTO);
    void eliminarMovimiento(Long id);
}
