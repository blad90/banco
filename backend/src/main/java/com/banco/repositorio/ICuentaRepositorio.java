package com.banco.repositorio;

import com.banco.entidad.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICuentaRepositorio extends JpaRepository<Cuenta, Long> {
}
