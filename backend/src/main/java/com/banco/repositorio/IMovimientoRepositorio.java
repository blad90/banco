package com.banco.repositorio;

import com.banco.entidad.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMovimientoRepositorio extends JpaRepository<Movimiento, Long> {
}
