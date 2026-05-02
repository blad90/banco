package com.banco.repositorio;

import com.banco.entidad.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IClienteRepositorio extends JpaRepository<Cliente, Long> {
}
