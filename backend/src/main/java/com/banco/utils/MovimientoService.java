package com.banco.utils;

import com.banco.dto.MovimientoDTO;
import com.banco.entidad.Cuenta;
import com.banco.entidad.Movimiento;
import com.banco.excepciones.BusinessException;
import com.banco.excepciones.ResourceNotFoundException;
import com.banco.repositorio.ICuentaRepositorio;
import com.banco.repositorio.IMovimientoRepositorio;
import com.banco.servicio.IMovimientoServicio;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class MovimientoService implements IMovimientoServicio {

    private final IMovimientoRepositorio movimientoRepositorio;
    private final ICuentaRepositorio cuentaRepositorio;

    public MovimientoService(IMovimientoRepositorio movimientoRepositorio, ICuentaRepositorio cuentaRepositorio) {
        this.movimientoRepositorio = movimientoRepositorio;
        this.cuentaRepositorio = cuentaRepositorio;
    }

    @Value("${banco.limite.diario.retiro:1000}")
    private BigDecimal limiteDiario;

    @Transactional(readOnly = true)
    public List<MovimientoDTO> obtenerMovimientos() {
        return movimientoRepositorio.findAll().stream().map(MovimientoMapper::toDTO).toList();
    }

    @Transactional(readOnly = true)
    public MovimientoDTO obtenerMovimiento(Long id) {
        return MovimientoMapper.toDTO(movimientoRepositorio.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Movimiento no encontrado con el id: " + id)));
    }

    @Transactional(readOnly = true)
    public List<MovimientoDTO> obtenerMovimientoPorCuenta(Long cuentaId) {
        return movimientoRepositorio.findByCuentaId(cuentaId).stream().map(MovimientoMapper::toDTO).toList();
    }

    public MovimientoDTO crearMovimiento(MovimientoDTO movimientoDTO) {
        Cuenta cuenta = cuentaRepositorio.findById(movimientoDTO.getCuentaId())
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta no encontrada: " + movimientoDTO.getCuentaId()));

        if (!cuenta.getEstado()) {
            throw new BusinessException("La cuenta está inactiva");
        }

        String tipo = movimientoDTO.getTipoMovimiento().toUpperCase();
        BigDecimal valor = movimientoDTO.getValor().abs();

        if ("DEBITO".equals(tipo)) {
            if (cuenta.getSaldoDisponible().compareTo(BigDecimal.ZERO) == 0
                    || cuenta.getSaldoDisponible().compareTo(valor) < 0) {
                throw new BusinessException("Saldo no disponible");
            }
            LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
            LocalDateTime endOfDay = LocalDate.now().atTime(LocalTime.MAX);
            BigDecimal debitosHoy = movimientoRepositorio.sumDebitosDelDia(cuenta.getId(), startOfDay, endOfDay);
            if (debitosHoy.add(valor).compareTo(limiteDiario) > 0) {
                throw new BusinessException("Cupo diario excedido");
            }
            cuenta.setSaldoDisponible(cuenta.getSaldoDisponible().subtract(valor));
            valor = valor.negate();
        } else if ("CREDITO".equals(tipo)) {
            cuenta.setSaldoDisponible(cuenta.getSaldoDisponible().add(valor));
        } else {
            throw new BusinessException("Tipo de movimiento inválido!");
        }

        cuentaRepositorio.save(cuenta);

        Movimiento movimiento = new Movimiento();
        movimiento.setCuenta(cuenta);
        movimiento.setTipoMovimiento(tipo);
        movimiento.setValor(valor);
        movimiento.setSaldo(cuenta.getSaldoDisponible());

        return MovimientoMapper.toDTO(movimientoRepositorio.save(movimiento));
    }

    public void eliminarMovimiento(Long id) {
        movimientoRepositorio.findById(id).orElseThrow(() -> new ResourceNotFoundException("Movimiento no encontrado con el id: " + id));
        movimientoRepositorio.deleteById(id);
    }

}
