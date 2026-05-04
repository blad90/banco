package com.banco.utils;

import com.banco.dto.MovimientoDTO;
import com.banco.entidad.Movimiento;

public class MovimientoMapper {

    public static MovimientoDTO toDTO(Movimiento m) {
        return MovimientoDTO.builder()
                .id(m.getId())
                .fecha(m.getFecha())
                .tipoMovimiento(m.getTipoMovimiento())
                .valor(m.getValor())
                .saldo(m.getSaldo())
                .cuentaId(m.getCuenta().getId())
                .numeroCuenta(m.getCuenta().getNumeroCuenta())
                .tipoCuenta(m.getCuenta().getTipoCuenta())
                .build();
    }
}
