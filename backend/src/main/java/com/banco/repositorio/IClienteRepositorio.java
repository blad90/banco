package com.banco.repositorio;

import com.banco.dto.ClienteDTO;
import com.banco.entidad.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IClienteRepositorio extends JpaRepository<Cliente, Long> {

    @Query(value = """
    SELECT new com.banco.dto.ClienteDTO(c.nombre, c.genero, c.edad, c.identificacion, c.direccion, c.telefono, c.clienteId, c.contrasena, c.estado)
    FROM Cliente c
    WHERE LOWER(c.nombre) LIKE LOWER(CONCAT('%', :search, '%'))
    ORDER BY c.identificacion
""")
    Page<Cliente> findByClienteNombreContainingIgnoreCase(@Param("search") String nombre, Pageable pageable);
    Optional<Cliente> findClienteByClienteId(String clienteId);
    boolean existsByClienteId(String clienteId);
    boolean existsByIdentificacion(String identificacion);
}
