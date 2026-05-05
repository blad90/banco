package com.banco.utils;

import com.banco.dto.MovimientoDTO;
import com.banco.entidad.Movimiento;

public class MovimientoMapper {

    public static MovimientoDTO toDTO(Movimiento m) {
        return new MovimientoDTO(
                m.getId(),
                m.getFecha(),
                m.getTipoMovimiento(),
                m.getValor(),
                m.getSaldo(),
                m.getCuenta().getId(),
                m.getCuenta().getNumeroCuenta(),
                m.getCuenta().getTipoCuenta()
                );
    }
}
