package com.banco.repositorio;

import com.banco.entidad.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface IMovimientoRepositorio extends JpaRepository<Movimiento, Long> {
    List<Movimiento> findByCuentaId(Long cuentaId);

    @Query("SELECT COALESCE(SUM(ABS(m.valor)), 0) FROM Movimiento m " +
            "WHERE m.cuenta.id = :cuentaId " +
            "AND m.tipoMovimiento = 'DEBITO' " +
            "AND m.fecha >= :inicio AND m.fecha <= :fin")
    BigDecimal sumDebitosDelDia(@Param("cuentaId") Long cuentaId,
                                @Param("inicio") LocalDateTime inicio,
                                @Param("fin") LocalDateTime fin);

    @Query("SELECT m FROM Movimiento m " +
            "JOIN m.cuenta c " +
            "JOIN c.cliente cl " +
            "WHERE cl.id = :clienteId " +
            "AND m.fecha BETWEEN :inicio AND :fin " +
            "ORDER BY m.fecha DESC")
    List<Movimiento> findByClienteAndFechas(@Param("clienteId") Long clienteId,
                                            @Param("inicio") LocalDateTime inicio,
                                            @Param("fin") LocalDateTime fin);
}
